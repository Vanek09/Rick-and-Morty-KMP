package com.box.plantintest.presentation.common.designsystem.tokens

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class PlantinSpacing(
    val none: Dp = 0.dp,
    val xs: Dp = 4.dp,
    val sm: Dp = 8.dp,
    val md: Dp = 12.dp,
    val lg: Dp = 16.dp,
    val xl: Dp = 20.dp,
    val xxl: Dp = 24.dp,
    val xxxl: Dp = 32.dp,
)

@Immutable
data class PlantinRadius(
    val none: Dp = 0.dp,
    val xs: Dp = 4.dp,
    val sm: Dp = 8.dp,
    val md: Dp = 12.dp,
    val lg: Dp = 16.dp,
    val xl: Dp = 20.dp,
    val xxl: Dp = 28.dp,
    val full: Dp = 1000.dp, // Fully rounded
) {
    // Component-specific radius
    val button: Dp = md
    val chip: Dp = sm
}

@Immutable
data class PlantinElevation(
    val level0: Dp = 0.dp,
    val level1: Dp = 1.dp,
    val level2: Dp = 3.dp,
    val level3: Dp = 6.dp,
    val level4: Dp = 8.dp,
    val level5: Dp = 12.dp,
) {
    // Component-specific elevations
    val button: Dp = level1
}

@Immutable
data class PlantinSizes(
    val iconSmall: Dp = 16.dp,
    val iconMedium: Dp = 24.dp,
    val iconLarge: Dp = 32.dp,
    val avatarSmall: Dp = 32.dp,
    val avatarMedium: Dp = 48.dp,
    val avatarLarge: Dp = 72.dp,
    val buttonHeight: Dp = 48.dp,
    val textFieldHeight: Dp = 56.dp,
    val listItemHeight: Dp = 64.dp,
    val toolbarHeight: Dp = 56.dp,
    val fabSize: Dp = 56.dp,
    val chipHeight: Dp = 32.dp,
)

val DefaultPlantinSpacing = PlantinSpacing()
val DefaultPlantinRadius = PlantinRadius()
val DefaultPlantinElevation = PlantinElevation()
val DefaultPlantinSizes = PlantinSizes()
