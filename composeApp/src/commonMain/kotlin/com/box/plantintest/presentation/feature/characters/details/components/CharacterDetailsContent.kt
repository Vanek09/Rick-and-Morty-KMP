package com.box.plantintest.presentation.feature.characters.details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.box.plantintest.domain.model.Character
import com.box.plantintest.presentation.common.designsystem.utils.PlantinSpacer
import com.box.plantintest.presentation.common.designsystem.utils.screenPadding

@Immutable
data class CharacterDetailsData(
    val character: Character,
    val episodesExpanded: Boolean
)

@Immutable
data class CharacterDetailsCallbacks(
    val onBack: () -> Unit,
    val onEpisodesToggle: () -> Unit
)

@Composable
fun CharacterDetailsContent(
    data: CharacterDetailsData,
    callbacks: CharacterDetailsCallbacks,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()

    // Create stable data objects to prevent unnecessary recompositions
    val originInfo = remember(data.character.originName) {
        InfoCardData(title = "Origin", content = data.character.originName)
    }

    val locationInfo = remember(data.character.locationName) {
        InfoCardData(title = "Last known location", content = data.character.locationName)
    }

    val episodesData = remember(data.character.episodeUrls, data.episodesExpanded) {
        EpisodesData(
            episodes = data.character.episodeUrls,
            isExpanded = data.episodesExpanded,
            episodeCount = data.character.episodeUrls.size
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .screenPadding()
    ) {
        CharacterDetailsTopBar(onBack = callbacks.onBack)

        PlantinSpacer.Small()

        CharacterImageSection(
            imageUrl = data.character.imageUrl,
            contentDescription = data.character.name
        )

        PlantinSpacer.Large()

        CharacterInfoSection(character = data.character)

        PlantinSpacer.Large()

        CharacterInfoCard(data = originInfo)

        PlantinSpacer.Medium()

        CharacterInfoCard(data = locationInfo)

        PlantinSpacer.Large()

        EpisodesSection(
            episodesData = episodesData,
            onToggleExpand = callbacks.onEpisodesToggle
        )

        // Add bottom padding for better scrolling experience
        PlantinSpacer.Large()
    }
}
