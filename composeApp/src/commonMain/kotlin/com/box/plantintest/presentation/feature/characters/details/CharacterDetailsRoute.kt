package com.box.plantintest.presentation.feature.characters.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CharacterDetailsRoute(
    rootNavController: NavController,
    characterId: Int,
) {
    val viewModel: CharacterDetailsViewModel = koinViewModel()

    LaunchedEffect(Unit) {
        viewModel.effects.collectLatest { effect ->
            when (effect) {
                is CharacterDetailsEffect.ShowMessage -> {
                    // TODO: show snackbar/toast with effect.message
                }
            }
        }
    }

    CharacterDetailsScreen(
        id = characterId,
        viewModel = viewModel,
        onBack = { rootNavController.popBackStack() },
    )
}


