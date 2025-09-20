package com.box.plantintest.presentation.feature.characters.details.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.box.plantintest.presentation.common.designsystem.PlantinTheme

@Composable
fun CharacterDetailsTopBar(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = onBack,
        modifier = modifier.padding(PlantinTheme.spacing.lg)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = PlantinTheme.colors.onSurface
        )
    }
}
