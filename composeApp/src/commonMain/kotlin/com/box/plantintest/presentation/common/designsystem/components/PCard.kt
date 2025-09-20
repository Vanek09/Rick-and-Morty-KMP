package com.box.plantintest.presentation.common.designsystem.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.box.plantintest.presentation.common.designsystem.PlantinTheme

@Composable
fun PCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(PlantinTheme.radius.md),
        colors = CardDefaults.cardColors(
            containerColor = PlantinTheme.colors.surface,
            contentColor = PlantinTheme.colors.onSurface,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = PlantinTheme.elevation.level2)
    ) { content() }
}
