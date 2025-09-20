package com.box.plantintest.presentation.feature.characters.list.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.box.plantintest.presentation.feature.characters.list.CharactersListRoute
import kotlinx.serialization.Serializable

@Serializable
data object CharactersListScreenRoute

fun NavController.navigateToCharactersList(navOptions: NavOptions? = null) {
    navigate(CharactersListScreenRoute, navOptions)
}

fun NavController.navigateToCharactersListSingleTop() {
    val options = navOptions {
        popUpTo(graph.id) { inclusive = true }
        launchSingleTop = true
    }
    navigate(CharactersListScreenRoute, options)
}

fun NavGraphBuilder.charactersListScreen(rootNavController: NavController) {
    composable<CharactersListScreenRoute> {
        CharactersListRoute(rootNavController = rootNavController)
    }
}


