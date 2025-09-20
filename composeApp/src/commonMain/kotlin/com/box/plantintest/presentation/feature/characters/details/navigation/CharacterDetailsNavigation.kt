package com.box.plantintest.presentation.feature.characters.details.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.box.plantintest.presentation.feature.characters.details.CharacterDetailsRoute
import kotlinx.serialization.Serializable

@Serializable
data class CharacterDetailsScreenRoute(
    val id: Int,
)

fun NavController.navigateToCharacterDetails(
    id: Int,
    navOptions: NavOptions? = null,
) {
    navigate(CharacterDetailsScreenRoute(id = id), navOptions)
}

fun NavGraphBuilder.characterDetailsScreen(rootNavController: NavController) {
    composable<CharacterDetailsScreenRoute> { backStackEntry ->
        val route = backStackEntry.toRoute<CharacterDetailsScreenRoute>()
        CharacterDetailsRoute(
            rootNavController = rootNavController,
            characterId = route.id,
        )
    }
}


