package com.box.plantintest.presentation.common.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import com.box.plantintest.presentation.common.designsystem.tokens.DarkPlantinColors
import com.box.plantintest.presentation.common.designsystem.tokens.DarkStatusColors
import com.box.plantintest.presentation.common.designsystem.tokens.DefaultPlantinElevation
import com.box.plantintest.presentation.common.designsystem.tokens.DefaultPlantinRadius
import com.box.plantintest.presentation.common.designsystem.tokens.DefaultPlantinSizes
import com.box.plantintest.presentation.common.designsystem.tokens.DefaultPlantinSpacing
import com.box.plantintest.presentation.common.designsystem.tokens.DefaultPlantinTypography
import com.box.plantintest.presentation.common.designsystem.tokens.LightPlantinColors
import com.box.plantintest.presentation.common.designsystem.tokens.LightStatusColors
import com.box.plantintest.presentation.common.designsystem.tokens.PlantinColors
import com.box.plantintest.presentation.common.designsystem.tokens.PlantinElevation
import com.box.plantintest.presentation.common.designsystem.tokens.PlantinRadius
import com.box.plantintest.presentation.common.designsystem.tokens.PlantinSizes
import com.box.plantintest.presentation.common.designsystem.tokens.PlantinSpacing
import com.box.plantintest.presentation.common.designsystem.tokens.PlantinStatusColors
import com.box.plantintest.presentation.common.designsystem.tokens.PlantinTypography

// Composition Locals
private val LocalColors = staticCompositionLocalOf { LightPlantinColors }
private val LocalStatusColors = staticCompositionLocalOf { LightStatusColors }
private val LocalTypography = staticCompositionLocalOf { DefaultPlantinTypography }
private val LocalSpacing = staticCompositionLocalOf { DefaultPlantinSpacing }
private val LocalRadius = staticCompositionLocalOf { DefaultPlantinRadius }
private val LocalElevation = staticCompositionLocalOf { DefaultPlantinElevation }
private val LocalSizes = staticCompositionLocalOf { DefaultPlantinSizes }

@Stable
object PlantinTheme {
    /**
     * App color palette following Material 3 color system
     */
    val colors: PlantinColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    /**
     * Status-specific colors for character states
     */
    val statusColors: PlantinStatusColors
        @Composable
        @ReadOnlyComposable
        get() = LocalStatusColors.current

    /**
     * Typography scale following Material 3 type system
     */
    val typography: PlantinTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    /**
     * Spacing values for consistent layout
     */
    val spacing: PlantinSpacing
        @Composable
        @ReadOnlyComposable
        get() = LocalSpacing.current

    /**
     * Corner radius values for shapes
     */
    val radius: PlantinRadius
        @Composable
        @ReadOnlyComposable
        get() = LocalRadius.current

    /**
     * Elevation values for depth
     */
    val elevation: PlantinElevation
        @Composable
        @ReadOnlyComposable
        get() = LocalElevation.current

    /**
     * Size values for consistent component sizing
     */
    val sizes: PlantinSizes
        @Composable
        @ReadOnlyComposable
        get() = LocalSizes.current
}

/**
 * PlantinTheme composable that provides the design system context
 *
 * @param darkTheme Whether to use dark theme colors
 * @param content The content to be themed
 */
@Composable
fun PlantinTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) DarkPlantinColors else LightPlantinColors
    val statusColors = if (darkTheme) DarkStatusColors else LightStatusColors

    val materialColorScheme = if (darkTheme) {
        darkColorScheme(
            primary = colors.primary,
            onPrimary = colors.onPrimary,
            primaryContainer = colors.primaryContainer,
            onPrimaryContainer = colors.onPrimaryContainer,
            secondary = colors.secondary,
            onSecondary = colors.onSecondary,
            secondaryContainer = colors.secondaryContainer,
            onSecondaryContainer = colors.onSecondaryContainer,
            background = colors.background,
            onBackground = colors.onBackground,
            surface = colors.surface,
            onSurface = colors.onSurface,
            surfaceVariant = colors.surfaceVariant,
            onSurfaceVariant = colors.onSurfaceVariant,
            error = colors.error,
            onError = colors.onError,
            errorContainer = colors.errorContainer,
            onErrorContainer = colors.onErrorContainer,
            outline = colors.outline,
            outlineVariant = colors.outlineVariant,
            scrim = colors.scrim,
            inverseSurface = colors.inverseSurface,
            inverseOnSurface = colors.inverseOnSurface,
            inversePrimary = colors.inversePrimary,
        )
    } else {
        lightColorScheme(
            primary = colors.primary,
            onPrimary = colors.onPrimary,
            primaryContainer = colors.primaryContainer,
            onPrimaryContainer = colors.onPrimaryContainer,
            secondary = colors.secondary,
            onSecondary = colors.onSecondary,
            secondaryContainer = colors.secondaryContainer,
            onSecondaryContainer = colors.onSecondaryContainer,
            background = colors.background,
            onBackground = colors.onBackground,
            surface = colors.surface,
            onSurface = colors.onSurface,
            surfaceVariant = colors.surfaceVariant,
            onSurfaceVariant = colors.onSurfaceVariant,
            error = colors.error,
            onError = colors.onError,
            errorContainer = colors.errorContainer,
            onErrorContainer = colors.onErrorContainer,
            outline = colors.outline,
            outlineVariant = colors.outlineVariant,
            scrim = colors.scrim,
            inverseSurface = colors.inverseSurface,
            inverseOnSurface = colors.inverseOnSurface,
            inversePrimary = colors.inversePrimary,
        )
    }

    CompositionLocalProvider(
        LocalColors provides colors,
        LocalStatusColors provides statusColors,
        LocalTypography provides DefaultPlantinTypography,
        LocalSpacing provides DefaultPlantinSpacing,
        LocalRadius provides DefaultPlantinRadius,
        LocalElevation provides DefaultPlantinElevation,
        LocalSizes provides DefaultPlantinSizes,
    ) {
        MaterialTheme(
            colorScheme = materialColorScheme,
            typography = Typography(), // Use Material 3 default typography
            content = content,
        )
    }
}
