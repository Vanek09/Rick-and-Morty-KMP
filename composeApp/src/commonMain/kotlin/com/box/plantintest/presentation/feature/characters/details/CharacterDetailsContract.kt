package com.box.plantintest.presentation.feature.characters.details

import com.box.plantintest.base.UiEffect
import com.box.plantintest.base.UiEvent
import com.box.plantintest.base.UiState
import com.box.plantintest.domain.model.Character

data class CharacterDetailsState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val character: Character? = null,
) : UiState

sealed interface CharacterDetailsEvent : UiEvent {
    data class Init(val id: Int) : CharacterDetailsEvent
    data object Retry : CharacterDetailsEvent
}

sealed interface CharacterDetailsEffect : UiEffect
{
    data class ShowMessage(val message: String) : CharacterDetailsEffect
}
