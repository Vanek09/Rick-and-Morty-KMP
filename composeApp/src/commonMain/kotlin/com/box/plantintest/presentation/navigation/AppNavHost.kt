package com.box.plantintest.presentation.navigation

import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.box.plantintest.presentation.feature.characters.details.navigation.characterDetailsScreen
import com.box.plantintest.presentation.feature.characters.list.navigation.CharactersListScreenRoute
import com.box.plantintest.presentation.feature.characters.list.navigation.charactersListScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = CharactersListScreenRoute,
        modifier = Modifier.systemBarsPadding()
    ) {
        charactersListScreen(rootNavController = navController)
        characterDetailsScreen(rootNavController = navController)
    }
}


