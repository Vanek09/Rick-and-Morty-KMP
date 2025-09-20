package com.box.plantintest.presentation.common.designsystem.preview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.box.plantintest.domain.model.CharacterStatus
import com.box.plantintest.presentation.common.designsystem.PlantinTheme
import com.box.plantintest.presentation.common.designsystem.components.PButton
import com.box.plantintest.presentation.common.designsystem.components.PButtonSize
import com.box.plantintest.presentation.common.designsystem.components.PButtonState
import com.box.plantintest.presentation.common.designsystem.components.PButtonVariant
import com.box.plantintest.presentation.common.designsystem.components.PCard
import com.box.plantintest.presentation.common.designsystem.components.PChip
import com.box.plantintest.presentation.common.designsystem.components.PChipSize
import com.box.plantintest.presentation.common.designsystem.components.PChipVariant
import com.box.plantintest.presentation.common.designsystem.components.PCircularLoadingIndicator
import com.box.plantintest.presentation.common.designsystem.components.PIconButton
import com.box.plantintest.presentation.common.designsystem.components.PLoadingListPlaceholder
import com.box.plantintest.presentation.common.designsystem.components.PLoadingSize
import com.box.plantintest.presentation.common.designsystem.utils.PlantinSpacer
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Component preview screen for development and testing
 * This helps visualize all design system components in different states
 */
@Composable
fun ComponentPreviewScreen() {
    PlantinTheme {
        LazyColumn(
            modifier = Modifier.padding(PlantinTheme.spacing.lg),
            verticalArrangement = Arrangement.spacedBy(PlantinTheme.spacing.xl)
        ) {
            item {
                PreviewSection(title = "Colors") {
                    ColorPreview()
                }
            }

            item {
                PreviewSection(title = "Typography") {
                    TypographyPreview()
                }
            }

            item {
                PreviewSection(title = "Buttons") {
                    ButtonPreview()
                }
            }

            item {
                PreviewSection(title = "Chips") {
                    ChipPreview()
                }
            }

            item {
                PreviewSection(title = "Cards") {
                    CardPreview()
                }
            }

            item {
                PreviewSection(title = "Loading Indicators") {
                    LoadingPreview()
                }
            }
        }
    }
}

@Composable
private fun PreviewSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column {
        Text(
            text = title,
            style = PlantinTheme.typography.headlineSmall,
            color = PlantinTheme.colors.onSurface
        )
        PlantinSpacer.Small()
        content()
    }
}

@Composable
private fun ColorPreview() {
    val colors = listOf(
        "Primary" to PlantinTheme.colors.primary,
        "Secondary" to PlantinTheme.colors.secondary,
        "Surface" to PlantinTheme.colors.surface,
        "Error" to PlantinTheme.colors.error,
        "Success" to PlantinTheme.colors.success,
        "Warning" to PlantinTheme.colors.warning,
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(PlantinTheme.spacing.sm)
    ) {
        colors.forEach { (name, color) ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier
                        .height(40.dp)
                        .weight(0.3f),
                    color = color
                ) {}
                Spacer(modifier = Modifier.weight(0.1f))
                Text(
                    text = name,
                    modifier = Modifier.weight(0.6f),
                    style = PlantinTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun TypographyPreview() {
    val typeStyles = listOf(
        "Display Large" to PlantinTheme.typography.displayLarge,
        "Headline Large" to PlantinTheme.typography.headlineLarge,
        "Title Large" to PlantinTheme.typography.titleLarge,
        "Title Medium" to PlantinTheme.typography.titleMedium,
        "Body Large" to PlantinTheme.typography.bodyLarge,
        "Body Medium" to PlantinTheme.typography.bodyMedium,
        "Label Large" to PlantinTheme.typography.labelLarge,
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(PlantinTheme.spacing.sm)
    ) {
        typeStyles.forEach { (name, style) ->
            Column {
                Text(
                    text = name,
                    style = style,
                    color = PlantinTheme.colors.onSurface
                )
                Text(
                    text = "The quick brown fox jumps over the lazy dog",
                    style = style,
                    color = PlantinTheme.colors.onSurface
                )
            }
        }
    }
}

@Composable
private fun ButtonPreview() {
    var isLoading by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(PlantinTheme.spacing.md)
    ) {
        // Variants
        Text(
            text = "Variants",
            style = PlantinTheme.typography.titleMedium
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(PlantinTheme.spacing.md)
        ) {
            PButton(
                state = PButtonState(text = "Primary"),
                onClick = { isLoading = !isLoading },
                variant = PButtonVariant.Primary
            )
            PButton(
                state = PButtonState(text = "Secondary"),
                onClick = {},
                variant = PButtonVariant.Secondary
            )
            PButton(
                state = PButtonState(text = "Tertiary"),
                onClick = {},
                variant = PButtonVariant.Tertiary
            )
        }

        // Sizes
        Text(
            text = "Sizes",
            style = PlantinTheme.typography.titleMedium
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(PlantinTheme.spacing.sm)
        ) {
            PButton(
                state = PButtonState(text = "Small Button"),
                onClick = {},
                size = PButtonSize.Small
            )
            PButton(
                state = PButtonState(text = "Medium Button"),
                onClick = {},
                size = PButtonSize.Medium
            )
            PButton(
                state = PButtonState(text = "Large Button"),
                onClick = {},
                size = PButtonSize.Large
            )
        }

        // States
        Text(
            text = "States",
            style = PlantinTheme.typography.titleMedium
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(PlantinTheme.spacing.md)
        ) {
            PButton(
                state = PButtonState(
                    text = "Loading",
                    isLoading = isLoading
                ),
                onClick = {}
            )
            PButton(
                state = PButtonState(
                    text = "Disabled",
                    isEnabled = false
                ),
                onClick = {}
            )
        }

        // With icons
        Text(
            text = "With Icons",
            style = PlantinTheme.typography.titleMedium
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(PlantinTheme.spacing.sm)
        ) {
            PButton(
                state = PButtonState(
                    text = "Add Item",
                    leadingIcon = Icons.Default.Add
                ),
                onClick = {}
            )
            PButton(
                state = PButtonState(
                    text = "Favorite",
                    trailingIcon = Icons.Default.Favorite
                ),
                onClick = {}
            )
        }

        // Icon buttons
        Text(
            text = "Icon Buttons",
            style = PlantinTheme.typography.titleMedium
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(PlantinTheme.spacing.md)
        ) {
            PIconButton(
                onClick = {},
                icon = Icons.Default.Star,
                contentDescription = "Star",
                size = PButtonSize.Small
            )
            PIconButton(
                onClick = {},
                icon = Icons.Default.Star,
                contentDescription = "Star",
                size = PButtonSize.Medium
            )
            PIconButton(
                onClick = {},
                icon = Icons.Default.Star,
                contentDescription = "Star",
                size = PButtonSize.Large
            )
        }
    }
}

@Composable
private fun ChipPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(PlantinTheme.spacing.md)
    ) {
        // Character status chips
        Text(
            text = "Character Status",
            style = PlantinTheme.typography.titleMedium
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(PlantinTheme.spacing.md)
        ) {
            PChip(status = CharacterStatus.Alive)
            PChip(status = CharacterStatus.Dead)
            PChip(status = CharacterStatus.Unknown)
        }

        // Variants
        Text(
            text = "Variants",
            style = PlantinTheme.typography.titleMedium
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(PlantinTheme.spacing.sm)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(PlantinTheme.spacing.md)
            ) {
                PChip(
                    status = CharacterStatus.Alive,
                    variant = PChipVariant.Filled
                )
                PChip(
                    status = CharacterStatus.Alive,
                    variant = PChipVariant.Outlined
                )
                PChip(
                    status = CharacterStatus.Alive,
                    variant = PChipVariant.Tonal
                )
            }
        }

        // Sizes
        Text(
            text = "Sizes",
            style = PlantinTheme.typography.titleMedium
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(PlantinTheme.spacing.md),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PChip(
                status = CharacterStatus.Alive,
                size = PChipSize.Small
            )
            PChip(
                status = CharacterStatus.Alive,
                size = PChipSize.Medium
            )
            PChip(
                status = CharacterStatus.Alive,
                size = PChipSize.Large
            )
        }

        // Custom chips
        Text(
            text = "Custom Chips",
            style = PlantinTheme.typography.titleMedium
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(PlantinTheme.spacing.md)
        ) {
            PChip(text = "Category")
            PChip(
                text = "With Icon",
                leadingIcon = Icons.Default.Star
            )
        }
    }
}

@Composable
private fun CardPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(PlantinTheme.spacing.md)
    ) {
        PCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(PlantinTheme.spacing.lg)
            ) {
                Text(
                    text = "Basic Card",
                    style = PlantinTheme.typography.titleMedium
                )
                PlantinSpacer.Small()
                Text(
                    text = "This is a basic card component with standard styling and elevation.",
                    style = PlantinTheme.typography.bodyMedium
                )
            }
        }

        PCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(PlantinTheme.spacing.lg)
            ) {
                Text(
                    text = "Interactive Card",
                    style = PlantinTheme.typography.titleMedium
                )
                PlantinSpacer.Small()
                Text(
                    text = "Cards can contain any content including buttons and interactive elements.",
                    style = PlantinTheme.typography.bodyMedium
                )
                PlantinSpacer.Medium()
                Row(
                    horizontalArrangement = Arrangement.spacedBy(PlantinTheme.spacing.md)
                ) {
                    PButton(
                        state = PButtonState(text = "Action"),
                        onClick = {},
                        variant = PButtonVariant.Primary,
                        size = PButtonSize.Small
                    )
                    PButton(
                        state = PButtonState(text = "Cancel"),
                        onClick = {},
                        variant = PButtonVariant.Tertiary,
                        size = PButtonSize.Small
                    )
                }
            }
        }
    }
}

@Composable
private fun LoadingPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(PlantinTheme.spacing.md)
    ) {
        Text(
            text = "Circular Indicators",
            style = PlantinTheme.typography.titleMedium
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(PlantinTheme.spacing.lg),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PCircularLoadingIndicator(size = PLoadingSize.Small)
            PCircularLoadingIndicator(size = PLoadingSize.Medium)
            PCircularLoadingIndicator(size = PLoadingSize.Large)
            PCircularLoadingIndicator(size = PLoadingSize.XLarge)
        }

        Text(
            text = "List Placeholder",
            style = PlantinTheme.typography.titleMedium
        )
        PLoadingListPlaceholder(itemCount = 3)
    }
}

// Preview annotations for development
@Preview(showBackground = true)
@Composable
private fun ComponentPreviewLight() {
    PlantinTheme(darkTheme = false) {
        ComponentPreviewScreen()
    }
}

@Preview(showBackground = true)
@Composable
private fun ComponentPreviewDark() {
    PlantinTheme(darkTheme = true) {
        ComponentPreviewScreen()
    }
}
