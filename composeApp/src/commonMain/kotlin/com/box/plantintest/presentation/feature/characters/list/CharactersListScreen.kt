package com.box.plantintest.presentation.feature.characters.list

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.box.plantintest.domain.model.CharacterFilters
import com.box.plantintest.domain.model.CharacterSort
import com.box.plantintest.presentation.common.designsystem.components.PEmptyStub
import com.box.plantintest.presentation.common.designsystem.components.PErrorStub
import com.box.plantintest.presentation.common.designsystem.components.PLoadingListPlaceholder
import com.box.plantintest.presentation.common.designsystem.utils.screenPadding
import com.box.plantintest.presentation.feature.characters.list.components.CharactersFilterSheet
import com.box.plantintest.presentation.feature.characters.list.components.CharactersList
import com.box.plantintest.presentation.feature.characters.list.components.CharactersListData
import com.box.plantintest.presentation.feature.characters.list.components.CharactersTopBar
import com.box.plantintest.presentation.feature.characters.list.components.FilterSheetState
import com.box.plantintest.presentation.feature.characters.list.components.PullRefreshContent
import com.box.plantintest.presentation.feature.characters.list.components.SearchState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CharactersListScreen(
    viewModel: CharactersListViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    var showFilterSheet by remember { mutableStateOf(false) }
    var draftFilters by remember { mutableStateOf(state.filters) }
    var draftSort by remember { mutableStateOf(state.filters.sort) }

    // Initialize screen on first composition
    LaunchedEffect(Unit) {
        viewModel.setEvent(CharactersListEvent.Init)
    }

    // Sync draft filters with actual state when sheet is hidden
    LaunchedEffect(state.filters, showFilterSheet) {
        if (!showFilterSheet) {
            draftFilters = state.filters
            draftSort = state.filters.sort
        }
    }

    // Create stable callback references to prevent recomposition
    val callbacks = remember {
        CharactersListCallbacks(
            onSearchChanged = { query -> viewModel.setEvent(CharactersListEvent.SearchChanged(query)) },
            onSearch = { viewModel.setEvent(CharactersListEvent.Retry) },
            onRefresh = { viewModel.setEvent(CharactersListEvent.Refresh) },
            onLoadMore = { viewModel.setEvent(CharactersListEvent.LoadNextPage) },
            onRetry = { viewModel.setEvent(CharactersListEvent.Retry) },
            onItemClick = { id -> viewModel.setEvent(CharactersListEvent.OpenDetails(id)) },
            onOpenFilters = { showFilterSheet = true },
            onFiltersChange = { filters -> draftFilters = filters },
            onSortChange = { sort -> draftSort = sort },
            onApplyFilters = {
                viewModel.setEvent(CharactersListEvent.FilterChanged(draftFilters))
                viewModel.setEvent(CharactersListEvent.SortChanged(draftSort))
                showFilterSheet = false
            },
            onDismissFilters = { showFilterSheet = false }
        )
    }

    CharactersListContent(
        state = state,
        filterSheetState = FilterSheetState(
            filters = draftFilters,
            sort = draftSort,
            isVisible = showFilterSheet
        ),
        callbacks = callbacks
    )
}

/**
 * Stable container for all screen callbacks to prevent unnecessary recompositions
 */
data class CharactersListCallbacks(
    val onSearchChanged: (String) -> Unit,
    val onSearch: () -> Unit,
    val onRefresh: () -> Unit,
    val onLoadMore: () -> Unit,
    val onRetry: () -> Unit,
    val onItemClick: (Int) -> Unit,
    val onOpenFilters: () -> Unit,
    val onFiltersChange: (CharacterFilters) -> Unit,
    val onSortChange: (CharacterSort) -> Unit,
    val onApplyFilters: () -> Unit,
    val onDismissFilters: () -> Unit
)

@Composable
private fun CharactersListContent(
    state: CharactersListState,
    filterSheetState: FilterSheetState,
    callbacks: CharactersListCallbacks,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            CharactersTopBar(
                searchState = SearchState(
                    query = state.query.orEmpty(),
                    onQueryChange = callbacks.onSearchChanged,
                    onSearch = callbacks.onSearch
                ),
                onOpenFilters = callbacks.onOpenFilters
            )
        },
        modifier = modifier
    ) { paddingValues ->
        PullRefreshContent(
            isRefreshing = state.isRefreshing,
            onRefresh = callbacks.onRefresh,
            modifier = Modifier.padding(paddingValues)
        ) {
            CharactersListMainContent(
                state = state,
                onLoadMore = callbacks.onLoadMore,
                onItemClick = callbacks.onItemClick,
                onRetry = callbacks.onRetry
            )
        }

        CharactersFilterSheet(
            state = filterSheetState,
            onFiltersChange = callbacks.onFiltersChange,
            onSortChange = callbacks.onSortChange,
            onApply = callbacks.onApplyFilters,
            onDismiss = callbacks.onDismissFilters
        )
    }
}

@Composable
private fun CharactersListMainContent(
    state: CharactersListState,
    onLoadMore: () -> Unit,
    onItemClick: (Int) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    val isInitialLoading = state.isLoading && state.items.isEmpty()
    val hasErrorAndEmpty = state.error != null && state.items.isEmpty()

    // Create a stable content state for AnimatedContent
    val contentState = remember(isInitialLoading, state.error, state.isEmpty) {
        when {
            isInitialLoading -> ContentState.Loading
            hasErrorAndEmpty -> ContentState.Error(state.error)
            state.isEmpty -> ContentState.Empty
            else -> ContentState.Content
        }
    }

    AnimatedContent(
        targetState = contentState,
        modifier = modifier.fillMaxSize()
    ) { targetState ->
        when (targetState) {
            ContentState.Loading -> {
                PLoadingListPlaceholder(
                    modifier = Modifier
                        .fillMaxSize()
                        .screenPadding(),
                    itemCount = 10
                )
            }

            is ContentState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    PErrorStub(
                        message = targetState.message ?: "An error occurred",
                        onRetry = onRetry
                    )
                }
            }

            ContentState.Empty -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    PEmptyStub(
                        title = "Nothing found",
                        subtitle = "Try adjusting your search or filters"
                    )
                }
            }

            ContentState.Content -> {
                CharactersList(
                    data = CharactersListData(
                        items = state.items,
                        isLoadingMore = state.isLoadingMore,
                        canLoadMore = state.canLoadMore
                    ),
                    onItemClick = onItemClick,
                    onLoadMore = onLoadMore,
                    listState = listState
                )
            }
        }
    }
}

/**
 * Sealed class representing different content states for better type safety
 * and more explicit state management
 */
private sealed interface ContentState {
    data object Loading : ContentState
    data class Error(val message: String?) : ContentState
    data object Empty : ContentState
    data object Content : ContentState
}


