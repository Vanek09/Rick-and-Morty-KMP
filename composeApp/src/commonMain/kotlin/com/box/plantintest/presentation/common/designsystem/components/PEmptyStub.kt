package com.box.plantintest.presentation.common.designsystem.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.box.plantintest.presentation.common.designsystem.PlantinTheme

@Composable
fun PEmptyStub(title: String, subtitle: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(Icons.Default.Inbox, contentDescription = null, tint = PlantinTheme.colors.outline)
        Spacer(Modifier.height(PlantinTheme.spacing.md))
        Text(
            text = title,
            style = PlantinTheme.typography.titleMedium,
            color = PlantinTheme.colors.onSurface
        )
        Spacer(Modifier.height(PlantinTheme.spacing.sm))
        Text(
            text = subtitle,
            style = PlantinTheme.typography.body,
            color = PlantinTheme.colors.onSurface.copy(alpha = 0.7f),
            textAlign = TextAlign.Center,
        )
    }
}
