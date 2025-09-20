package com.box.plantintest.presentation.feature.characters.list

import com.box.plantintest.base.UiEffect
import com.box.plantintest.base.UiEvent
import com.box.plantintest.base.UiState
import com.box.plantintest.domain.model.Character
import com.box.plantintest.domain.model.CharacterFilters
import com.box.plantintest.domain.model.CharacterSort
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class CharactersListState(
    val items: ImmutableList<Character> = persistentListOf(),
    val query: String? = null,
    val filters: CharacterFilters = CharacterFilters(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isLoadingMore: Boolean = false,
    val canLoadMore: Boolean = false,
    val error: String? = null,
    val isEmpty: Boolean = false,
) : UiState

sealed interface CharactersListEvent : UiEvent {
    data object Init : CharactersListEvent
    data class SearchChanged(val value: String) : CharactersListEvent
    data class FilterChanged(val filters: CharacterFilters) : CharactersListEvent
    data class SortChanged(val sort: CharacterSort) : CharactersListEvent
    data object Retry : CharactersListEvent
    data object LoadNextPage : CharactersListEvent
    data object Refresh : CharactersListEvent
    data class OpenDetails(val id: Int) : CharactersListEvent
}

sealed interface CharactersListEffect : UiEffect {
    data class NavigateToDetails(val id: Int) : CharactersListEffect
    data class ShowMessage(val message: String) : CharactersListEffect
}

