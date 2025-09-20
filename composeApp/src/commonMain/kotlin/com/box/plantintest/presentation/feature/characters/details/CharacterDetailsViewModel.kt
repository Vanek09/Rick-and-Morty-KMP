package com.box.plantintest.presentation.feature.characters.details

import com.box.plantintest.base.BaseViewModel
import com.box.plantintest.domain.usecase.GetCharacterDetailsUseCase
import com.box.plantintest.utils.subscribe
import com.box.plantintest.utils.toUserMessage
import kotlinx.coroutines.Job

class CharacterDetailsViewModel(
    private val getCharacterDetails: GetCharacterDetailsUseCase,
) : BaseViewModel<CharacterDetailsState, CharacterDetailsEvent, CharacterDetailsEffect>() {

    override fun createInitialState() = CharacterDetailsState()

    // State management
    private var currentCharacterId: Int? = null
    private var loadingJob: Job? = null

    companion object {
        private const val INVALID_CHARACTER_ID = -1
    }

    override fun handleEvent(event: CharacterDetailsEvent) {
        when (event) {
            is CharacterDetailsEvent.Init -> loadCharacterDetails(event.id)
            is CharacterDetailsEvent.Retry -> handleRetry()
        }
    }

    override fun onCleared() {
        super.onCleared()
        cancelLoadingJob()
    }

    // MARK: - Event Handlers

    private fun handleRetry() {
        currentCharacterId?.let { id ->
            if (id > INVALID_CHARACTER_ID) {
                loadCharacterDetails(id)
            } else {
                handleInvalidId()
            }
        } ?: handleInvalidId()
    }

    // MARK: - Core Loading Logic

    private fun loadCharacterDetails(id: Int) {
        // Validate input
        if (id <= INVALID_CHARACTER_ID) {
            handleInvalidId()
            return
        }

        // Avoid reload if same character
        if (currentCharacterId == id && currentState.character != null && currentState.error == null) {
            return
        }

        // Update state and start loading
        currentCharacterId = id
        startLoading()

        loadingJob = getCharacterDetails(id).subscribe(
            scope = this,
            success = { character -> handleLoadSuccess(character) },
            error = { throwable -> handleLoadError(throwable) }
        )
    }

    // MARK: - State Management

    private fun startLoading() {
        cancelLoadingJob()
        setState {
            copy(
                isLoading = true,
                error = null
            )
        }
    }

    private fun handleLoadSuccess(character: com.box.plantintest.domain.model.Character) {
        setState {
            copy(
                isLoading = false,
                character = character,
                error = null
            )
        }
    }

    private fun handleLoadError(throwable: Throwable) {
        val message = throwable.toUserMessage()

        setState {
            copy(
                isLoading = false,
                error = message
            )
        }

        setEffect { CharacterDetailsEffect.ShowMessage(message) }
    }

    private fun handleInvalidId() {
        val message = "Invalid character ID"

        setState {
            copy(
                isLoading = false,
                error = message,
                character = null
            )
        }

        setEffect { CharacterDetailsEffect.ShowMessage(message) }
    }

    // MARK: - Helper Methods

    private fun cancelLoadingJob() {
        loadingJob?.cancel()
        loadingJob = null
    }
}

