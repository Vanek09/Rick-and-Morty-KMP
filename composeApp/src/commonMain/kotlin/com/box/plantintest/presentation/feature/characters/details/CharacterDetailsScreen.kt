package com.box.plantintest.presentation.feature.characters.details

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.box.plantintest.presentation.common.designsystem.components.PEmptyStub
import com.box.plantintest.presentation.common.designsystem.components.PErrorStub
import com.box.plantintest.presentation.feature.characters.details.components.CharacterDetailsCallbacks
import com.box.plantintest.presentation.feature.characters.details.components.CharacterDetailsContent
import com.box.plantintest.presentation.feature.characters.details.components.CharacterDetailsData
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CharacterDetailsScreen(
    id: Int,
    viewModel: CharacterDetailsViewModel = koinViewModel(),
    onBack: () -> Unit = {},
) {
    val state by viewModel.uiState.collectAsState()
    var episodesExpanded by rememberSaveable { mutableStateOf(false) }

    // Initialize screen with character ID
    LaunchedEffect(id) {
        viewModel.setEvent(CharacterDetailsEvent.Init(id))
    }

    // Create stable callback references to prevent recomposition
    val callbacks = remember {
        CharacterDetailsCallbacks(
            onBack = onBack,
            onEpisodesToggle = { episodesExpanded = !episodesExpanded }
        )
    }

    CharacterDetailsMainContent(
        state = state,
        episodesExpanded = episodesExpanded,
        callbacks = callbacks,
        onRetry = { viewModel.setEvent(CharacterDetailsEvent.Retry) }
    )
}

@Composable
private fun CharacterDetailsMainContent(
    state: CharacterDetailsState,
    episodesExpanded: Boolean,
    callbacks: CharacterDetailsCallbacks,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isInitialLoading = state.isLoading && state.character == null
    val hasErrorAndNoData = state.error != null && state.character == null
    
    // Create a stable content state for AnimatedContent
    val contentState = remember(isInitialLoading, state.error, state.character) {
        when {
            isInitialLoading -> ContentState.Loading
            hasErrorAndNoData -> ContentState.Error(state.error)
            state.character == null -> ContentState.Empty
            else -> ContentState.Content
        }
    }
    
    AnimatedContent(
        targetState = contentState,
        modifier = modifier.fillMaxSize()
    ) { targetState ->
        when (targetState) {
            ContentState.Loading -> {
                LoadingContent()
            }
            
            is ContentState.Error -> {
                ErrorContent(
                    error = targetState.message ?: "An error occurred",
                    onRetry = onRetry
                )
            }
            
            ContentState.Empty -> {
                EmptyContent()
            }
            
            ContentState.Content -> {
                state.character?.let { character ->
                    CharacterDetailsContent(
                        data = CharacterDetailsData(
                            character = character,
                            episodesExpanded = episodesExpanded
                        ),
                        callbacks = callbacks
                    )
                }
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

@Composable
private fun LoadingContent(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorContent(
    error: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        PErrorStub(message = error, onRetry = onRetry)
    }
}

@Composable
private fun EmptyContent(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        PEmptyStub(
            title = "No details",
            subtitle = "Character not found"
        )
    }
}
