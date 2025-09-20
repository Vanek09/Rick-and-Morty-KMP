package com.box.plantintest.presentation.feature.characters.details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.box.plantintest.presentation.common.designsystem.PlantinTheme
import com.box.plantintest.presentation.common.designsystem.components.PButton
import com.box.plantintest.presentation.common.designsystem.components.PButtonState
import com.box.plantintest.presentation.common.designsystem.components.PButtonVariant
import com.box.plantintest.presentation.common.designsystem.components.PCard
import com.box.plantintest.presentation.common.designsystem.utils.cardPadding

@Immutable
data class EpisodesData(
    val episodes: List<String>,
    val isExpanded: Boolean,
    val episodeCount: Int
)

@Composable
fun EpisodesSection(
    episodesData: EpisodesData,
    onToggleExpand: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val processedEpisodes = remember(episodesData.episodes) {
        episodesData.episodes.map { url ->
            url.substringAfterLast('/')
        }
    }

    PCard(modifier = modifier) {
        Column(modifier = Modifier.cardPadding()) {
            Text(
                text = "Episodes (${episodesData.episodeCount})",
                style = PlantinTheme.typography.titleMedium,
                color = PlantinTheme.colors.onSurface
            )

            Spacer(Modifier.height(PlantinTheme.spacing.sm))

            PButton(
                state = PButtonState(
                    text = if (episodesData.isExpanded) "Hide" else "Show"
                ),
                onClick = onToggleExpand,
                variant = PButtonVariant.Secondary
            )

            if (episodesData.isExpanded) {
                Spacer(Modifier.height(PlantinTheme.spacing.sm))

                // Use simple Column because the parent already scrolls; avoid nested lazy scrollables
                processedEpisodes.forEach { episodeId ->
                    Text(
                        text = episodeId,
                        style = PlantinTheme.typography.body,
                        color = PlantinTheme.colors.onSurface.copy(alpha = 0.85f)
                    )
                    Spacer(Modifier.height(PlantinTheme.spacing.xs))
                }
            }
        }
    }
}
