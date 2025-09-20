package com.box.plantintest.presentation.feature.characters.details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import com.box.plantintest.presentation.common.designsystem.PlantinTheme
import com.box.plantintest.presentation.common.designsystem.components.PCard
import com.box.plantintest.presentation.common.designsystem.utils.cardPadding

@Immutable
data class InfoCardData(
    val title: String,
    val content: String
)

@Composable
fun CharacterInfoCard(
    data: InfoCardData,
    modifier: Modifier = Modifier,
) {
    PCard(modifier = modifier) {
        Column(modifier = Modifier.cardPadding()) {
            Text(
                text = data.title,
                style = PlantinTheme.typography.titleMedium,
                color = PlantinTheme.colors.onSurface
            )

            Spacer(Modifier.height(PlantinTheme.spacing.xs))

            Text(
                text = data.content,
                style = PlantinTheme.typography.body,
                color = PlantinTheme.colors.onSurface
            )
        }
    }
}
