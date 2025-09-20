package com.box.plantintest.presentation.feature.characters.details.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.box.plantintest.presentation.common.designsystem.PlantinTheme

@Composable
fun CharacterImageSection(
    imageUrl: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        model = imageUrl,
        contentDescription = contentDescription,
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp)
            .clip(RoundedCornerShape(PlantinTheme.radius.lg)),
        contentScale = ContentScale.Crop,
        placeholder = ColorPainter(PlantinTheme.colors.outline),
        error = ColorPainter(PlantinTheme.colors.outline),
    )
}
