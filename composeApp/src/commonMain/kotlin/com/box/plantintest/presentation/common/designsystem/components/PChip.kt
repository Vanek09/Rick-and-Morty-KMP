package com.box.plantintest.presentation.common.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.box.plantintest.domain.model.CharacterStatus
import com.box.plantintest.presentation.common.designsystem.PlantinTheme

/**
 * Chip variants for different use cases
 */
@Immutable
enum class PChipVariant {
    Filled,     // Solid background
    Outlined,   // Border with transparent background
    Tonal,      // Light background with strong text
}

/**
 * Chip sizes
 */
@Immutable
enum class PChipSize {
    Small,      // Compact display
    Medium,     // Default size
    Large,      // Prominent display
}

/**
 * Stable configuration for chip appearance
 */
@Stable
@Immutable
data class PChipColors(
    val containerColor: Color,
    val contentColor: Color,
    val borderColor: Color = Color.Transparent,
)

/**
 * Enhanced chip component with flexible styling options
 *
 * @param text Text to display in the chip
 * @param modifier Modifier for styling
 * @param variant Visual variant of the chip
 * @param size Size variant of the chip
 * @param leadingIcon Optional leading icon
 * @param colors Custom colors (optional)
 */
@Composable
fun PChip(
    text: String,
    modifier: Modifier = Modifier,
    variant: PChipVariant = PChipVariant.Filled,
    size: PChipSize = PChipSize.Medium,
    leadingIcon: ImageVector? = null,
    colors: PChipColors? = null,
) {
    val chipColors = colors ?: when (variant) {
        PChipVariant.Filled -> PChipColors(
            containerColor = PlantinTheme.colors.secondaryContainer,
            contentColor = PlantinTheme.colors.onSecondaryContainer
        )

        PChipVariant.Outlined -> PChipColors(
            containerColor = Color.Transparent,
            contentColor = PlantinTheme.colors.onSurface,
            borderColor = PlantinTheme.colors.outline
        )

        PChipVariant.Tonal -> PChipColors(
            containerColor = PlantinTheme.colors.surfaceVariant,
            contentColor = PlantinTheme.colors.onSurfaceVariant
        )
    }

    val shape = RoundedCornerShape(PlantinTheme.radius.chip)
    val textStyle = when (size) {
        PChipSize.Small -> PlantinTheme.typography.labelSmall
        PChipSize.Medium -> PlantinTheme.typography.labelMedium
        PChipSize.Large -> PlantinTheme.typography.labelLarge
    }
    val iconSize = when (size) {
        PChipSize.Small -> 12.dp
        PChipSize.Medium -> 16.dp
        PChipSize.Large -> 20.dp
    }
    val padding = when (size) {
        PChipSize.Small -> PlantinTheme.spacing.xs to PlantinTheme.spacing.xs
        PChipSize.Medium -> PlantinTheme.spacing.sm to PlantinTheme.spacing.xs
        PChipSize.Large -> PlantinTheme.spacing.md to PlantinTheme.spacing.sm
    }

    Box(
        modifier = modifier
            .clip(shape)
            .background(chipColors.containerColor)
            .then(
                if (chipColors.borderColor != Color.Transparent) {
                    Modifier.border(1.dp, chipColors.borderColor, shape)
                } else {
                    Modifier
                }
            )
            .padding(horizontal = padding.first, vertical = padding.second),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(PlantinTheme.spacing.xs),
            verticalAlignment = Alignment.CenterVertically
        ) {
            leadingIcon?.let { icon ->
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = chipColors.contentColor,
                    modifier = Modifier.size(iconSize)
                )
            }

            Text(
                text = text,
                color = chipColors.contentColor,
                style = textStyle,
                textAlign = TextAlign.Center,
            )
        }
    }
}

/**
 * Character status chip with predefined styling
 */
@Composable
fun PChip(
    status: CharacterStatus,
    modifier: Modifier = Modifier,
    variant: PChipVariant = PChipVariant.Filled,
    size: PChipSize = PChipSize.Medium,
) {
    // Access theme colors first
    val statusColors = PlantinTheme.statusColors

    val statusInfo = remember(status) {
        when (status) {
            CharacterStatus.Alive -> "ALIVE"
            CharacterStatus.Dead -> "DEAD"
            CharacterStatus.Unknown -> "UNKNOWN"
        }
    }

    val chipColors = remember(status, variant, statusColors) {
        val baseColor = when (status) {
            CharacterStatus.Alive -> statusColors.alive
            CharacterStatus.Dead -> statusColors.dead
            CharacterStatus.Unknown -> statusColors.unknown
        }
        val onBaseColor = when (status) {
            CharacterStatus.Alive -> statusColors.onAlive
            CharacterStatus.Dead -> statusColors.onDead
            CharacterStatus.Unknown -> statusColors.onUnknown
        }

        when (variant) {
            PChipVariant.Filled -> PChipColors(
                containerColor = baseColor,
                contentColor = onBaseColor
            )

            PChipVariant.Outlined -> PChipColors(
                containerColor = Color.Transparent,
                contentColor = baseColor,
                borderColor = baseColor
            )

            PChipVariant.Tonal -> PChipColors(
                containerColor = baseColor.copy(alpha = 0.12f),
                contentColor = baseColor
            )
        }
    }

    PChip(
        text = statusInfo,
        modifier = modifier,
        variant = variant,
        size = size,
        colors = chipColors
    )
}
