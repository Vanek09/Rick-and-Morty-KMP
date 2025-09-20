package com.box.plantintest.presentation.feature.characters.details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.box.plantintest.domain.model.Character
import com.box.plantintest.domain.model.CharacterStatus
import com.box.plantintest.presentation.common.designsystem.PlantinTheme
import com.box.plantintest.presentation.common.designsystem.components.PChip

@Immutable
data class CharacterInfo(
    val name: String,
    val status: CharacterStatus,
    val metadata: String
)

@Composable
fun CharacterInfoSection(
    character: Character,
    modifier: Modifier = Modifier,
) {
    val characterInfo = remember(character.name, character.status, character.species, character.gender) {
        CharacterInfo(
            name = character.name,
            status = character.status,
            metadata = listOf(character.species, character.gender.name).joinToString(" · ")
        )
    }
    
    Column(modifier = modifier) {
        Text(
            text = characterInfo.name,
            style = PlantinTheme.typography.titleLarge,
            color = PlantinTheme.colors.onSurface
        )
        
        Spacer(Modifier.height(PlantinTheme.spacing.xs))
        
        PChip(status = characterInfo.status)
        
        Spacer(Modifier.height(PlantinTheme.spacing.xs))
        
        Text(
            text = characterInfo.metadata,
            style = PlantinTheme.typography.body,
            color = PlantinTheme.colors.onSurface.copy(alpha = 0.8f)
        )
    }
}
