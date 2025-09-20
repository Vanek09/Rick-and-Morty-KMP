package com.box.plantintest.presentation.feature.characters.list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.box.plantintest.domain.model.Character
import com.box.plantintest.presentation.common.designsystem.PlantinTheme
import com.box.plantintest.presentation.common.designsystem.components.PCard
import com.box.plantintest.presentation.common.designsystem.components.PChip
import com.box.plantintest.presentation.common.designsystem.utils.PlantinSpacer
import com.box.plantintest.presentation.common.designsystem.utils.plantinClickable

@Immutable
private data class CharacterMetadata(
    val text: String
)

@Composable
fun CharacterItemCard(
    character: Character,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val metadata = remember(character.species, character.gender) {
        CharacterMetadata(
            text = listOf(character.species, character.gender.name)
                .joinToString(" · ")
        )
    }

    PCard(
        modifier = modifier.plantinClickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(PlantinTheme.spacing.md)
        ) {
            AsyncImage(
                model = character.imageUrl,
                contentDescription = character.name,
                modifier = Modifier
                    .height(72.dp)
                    .width(72.dp)
                    .clip(RoundedCornerShape(PlantinTheme.radius.sm)),
                contentScale = ContentScale.Crop,
                placeholder = ColorPainter(PlantinTheme.colors.outline),
                error = ColorPainter(PlantinTheme.colors.outline),
            )

            PlantinSpacer.Horizontal(PlantinTheme.spacing.md)

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = character.name,
                    style = PlantinTheme.typography.titleMedium,
                    color = PlantinTheme.colors.onSurface
                )

                PlantinSpacer.XSmall()

                PChip(status = character.status)

                PlantinSpacer.XSmall()

                Text(
                    text = metadata.text,
                    style = PlantinTheme.typography.body,
                    color = PlantinTheme.colors.onSurface.copy(alpha = 0.8f)
                )
            }
        }
    }
}
