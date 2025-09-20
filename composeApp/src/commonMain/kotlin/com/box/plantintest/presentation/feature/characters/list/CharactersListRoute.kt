package com.box.plantintest.presentation.feature.characters.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel
import androidx.navigation.NavController
import com.box.plantintest.presentation.feature.characters.details.navigation.navigateToCharacterDetails

@Composable
fun CharactersListRoute(
    rootNavController: NavController,
) {
    val viewModel: CharactersListViewModel = koinViewModel()

    LaunchedEffect(Unit) {
        viewModel.effects.collectLatest { effect ->
            when (effect) {
                is CharactersListEffect.NavigateToDetails -> {
                    rootNavController.navigateToCharacterDetails(
                        id = effect.id,
                    )
                }
                is CharactersListEffect.ShowMessage -> {
                    // TODO: show snackbar/toast with effect.message
                }
            }
        }
    }

    CharactersListScreen(viewModel = viewModel)
}


