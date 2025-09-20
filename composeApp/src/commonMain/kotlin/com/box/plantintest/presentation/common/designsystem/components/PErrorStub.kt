package com.box.plantintest.presentation.common.designsystem.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.box.plantintest.presentation.common.designsystem.PlantinTheme

@Composable
fun PErrorStub(message: String, onRetry: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            Icons.Default.ErrorOutline,
            contentDescription = null,
            tint = PlantinTheme.colors.error
        )
        Spacer(Modifier.height(PlantinTheme.spacing.md))
        Text(
            text = message,
            style = PlantinTheme.typography.body,
            color = PlantinTheme.colors.onSurface,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(PlantinTheme.spacing.sm))
        PButton(onClick = onRetry, text = "Retry")
    }
}
