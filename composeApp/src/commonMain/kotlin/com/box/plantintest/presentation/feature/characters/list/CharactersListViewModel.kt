package com.box.plantintest.presentation.feature.characters.list

import com.box.plantintest.base.BaseViewModel
import com.box.plantintest.domain.model.Character
import com.box.plantintest.domain.model.CharacterFilters
import com.box.plantintest.domain.model.CharacterSort
import com.box.plantintest.domain.model.CharacterStatus
import com.box.plantintest.domain.usecase.GetCharactersPageUseCase
import com.box.plantintest.utils.subscribe
import com.box.plantintest.utils.toUserMessage
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class CharactersListViewModel(
    private val getCharactersPage: GetCharactersPageUseCase,
) : BaseViewModel<CharactersListState, CharactersListEvent, CharactersListEffect>() {

    override fun createInitialState() = CharactersListState()

    private var currentPage: Int = 1
    private val searchFlow = MutableSharedFlow<String>(extraBufferCapacity = 1)

    private var searchJob: Job? = null
    private var loadingJob: Job? = null

    companion object {
        private const val SEARCH_DEBOUNCE_MS = 400L
    }

    private enum class LoadType {
        Initial,    // First load or after filter/sort change
        Refresh,    // Pull-to-refresh
        More        // Load next page
    }

    init {
        initializeSearch()
    }

    override fun handleEvent(event: CharactersListEvent) {
        when (event) {
            is CharactersListEvent.Init -> loadFirstPage()
            is CharactersListEvent.SearchChanged -> handleSearchChanged(event.value)
            is CharactersListEvent.FilterChanged -> handleFiltersChanged(event.filters)
            is CharactersListEvent.SortChanged -> handleSortChanged(event.sort)
            is CharactersListEvent.Retry -> loadFirstPage()
            is CharactersListEvent.LoadNextPage -> loadNextPage()
            is CharactersListEvent.Refresh -> refresh()
            is CharactersListEvent.OpenDetails -> {
                setEffect { CharactersListEffect.NavigateToDetails(event.id) }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        cancelAllJobs()
    }

    // MARK: - Public Methods

    private fun loadFirstPage() {
        loadData(
            page = 1,
            loadType = LoadType.Initial
        )
    }

    private fun refresh() {
        loadData(
            page = 1,
            loadType = LoadType.Refresh
        )
    }

    private fun loadNextPage() {
        if (!canLoadMore() || currentState.isLoadingMore) return

        loadData(
            page = currentPage + 1,
            loadType = LoadType.More
        )
    }

    // MARK: - Event Handlers

    private fun handleSearchChanged(value: String) {
        setState { copy(query = value) }
        searchFlow.tryEmit(value)
    }

    private fun handleFiltersChanged(filters: CharacterFilters) {
        if (currentState.filters != filters) {
            setState { copy(filters = filters) }
            resetAndReload()
        }
    }

    private fun handleSortChanged(sort: CharacterSort) {
        val newFilters = currentState.filters.copy(sort = sort)
        if (currentState.filters != newFilters) {
            setState { copy(filters = newFilters) }

            if (currentState.items.isNotEmpty()) {
                setState {
                    val resortedItems = applySorting(items, sort).toImmutableList()
                    copy(items = resortedItems)
                }
            } else {
                resetAndReload()
            }
        }
    }

    // MARK: - Core Loading Logic
    private fun loadData(page: Int, loadType: LoadType) {
        // Cancel any existing loading operation
        loadingJob?.cancel()

        // Update loading state based on load type
        updateLoadingState(loadType, isLoading = true)

        // Reset page to 1 for initial loads and filter changes
        if (loadType == LoadType.Initial || loadType == LoadType.Refresh) {
            currentPage = 1
        }

        // Load data with server-side filtering applied
        loadingJob = getCharactersPage(
            query = currentState.query,
            filters = currentState.filters,
            page = page,
        ).subscribe(
            scope = this,
            success = { paged ->
                handleLoadSuccess(paged, page, loadType)
            },
            error = { throwable ->
                handleLoadError(throwable, loadType)
            }
        )
    }

    private fun handleLoadSuccess(
        paged: com.box.plantintest.domain.model.PagedCharacters,
        page: Int,
        loadType: LoadType
    ) {
        setState {
            val accumulatedItems = when (loadType) {
                LoadType.Initial, LoadType.Refresh -> {
                    paged.items
                }

                LoadType.More -> {
                    items + paged.items
                }
            }

            val sortedItems = applySorting(accumulatedItems, filters.sort).toImmutableList()

            copy(
                items = sortedItems,
                canLoadMore = paged.nextPage != null,
                isEmpty = sortedItems.isEmpty(),
                error = null
            )
        }

        currentPage = page
        updateLoadingState(loadType, isLoading = false)
    }

    private fun handleLoadError(throwable: Throwable, loadType: LoadType) {
        val message = throwable.toUserMessage()

        setState { copy(error = message) }
        updateLoadingState(loadType, isLoading = false)
        setEffect { CharactersListEffect.ShowMessage(message) }
    }

    // MARK: - Helper Methods

    @OptIn(FlowPreview::class)
    private fun initializeSearch() {
        searchJob = launch {
            searchFlow
                .debounce(SEARCH_DEBOUNCE_MS)
                .distinctUntilChanged()
                .collect { resetAndReload() }
        }
    }

    private fun resetAndReload() {
        currentPage = 1
        loadData(page = 1, loadType = LoadType.Initial)
    }

    private fun updateLoadingState(loadType: LoadType, isLoading: Boolean) {
        setState {
            when (loadType) {
                LoadType.Initial -> copy(
                    isLoading = isLoading,
                    isRefreshing = false,
                    isLoadingMore = false
                )

                LoadType.Refresh -> copy(
                    isLoading = false,
                    isRefreshing = isLoading,
                    isLoadingMore = false
                )

                LoadType.More -> copy(
                    isLoading = false,
                    isRefreshing = false,
                    isLoadingMore = isLoading
                )
            }
        }
    }

    private fun canLoadMore(): Boolean {
        return currentState.canLoadMore && !currentState.isLoading &&
                !currentState.isRefreshing && !currentState.isLoadingMore
    }

    private fun cancelAllJobs() {
        searchJob?.cancel()
        loadingJob?.cancel()
        searchJob = null
        loadingJob = null
    }

    private fun applySorting(characters: List<Character>, sort: CharacterSort): List<Character> {
        return when (sort) {
            CharacterSort.None -> characters

            CharacterSort.NameAsc -> characters.sortedWith(
                compareBy<Character> { it.name.lowercase() }.thenBy { it.id }
            )

            CharacterSort.NameDesc -> characters.sortedWith(
                compareByDescending<Character> { it.name.lowercase() }.thenBy { it.id }
            )

            CharacterSort.CreatedAsc -> characters.sortedWith(
                compareBy<Character> { it.createdAtIso }.thenBy { it.id }
            )

            CharacterSort.CreatedDesc -> characters.sortedWith(
                compareByDescending<Character> { it.createdAtIso }.thenBy { it.id }
            )

            CharacterSort.StatusAsc -> characters.sortedWith(
                compareBy<Character> {
                    when (it.status) {
                        CharacterStatus.Alive -> 0
                        CharacterStatus.Dead -> 1
                        CharacterStatus.Unknown -> 2
                    }
                }.thenBy { it.id }
            )

            CharacterSort.SpeciesAsc -> characters.sortedWith(
                compareBy<Character> { it.species.lowercase() }.thenBy { it.id }
            )
        }
    }
}

