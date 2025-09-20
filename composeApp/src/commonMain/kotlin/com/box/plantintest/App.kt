package com.box.plantintest

import androidx.compose.runtime.Composable
import com.box.plantintest.presentation.common.designsystem.PlantinTheme
import com.box.plantintest.presentation.navigation.AppNavHost

@Composable
fun App() {
    PlantinTheme {
        AppNavHost()
    }
}


