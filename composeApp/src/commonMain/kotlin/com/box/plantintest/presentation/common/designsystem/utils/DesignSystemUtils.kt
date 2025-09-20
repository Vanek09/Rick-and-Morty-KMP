package com.box.plantintest.presentation.common.designsystem.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import com.box.plantintest.presentation.common.designsystem.PlantinTheme

/**
 * Utility functions and extensions for the design system
 */

/**
 * Clickable modifier with design system ripple effect
 */
@Composable
fun Modifier.plantinClickable(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit,
): Modifier {
    return this.clickable(
        enabled = enabled,
        onClickLabel = onClickLabel,
        role = role,
        interactionSource = remember { MutableInteractionSource() },
        indication = ripple(
            color = PlantinTheme.colors.primary
        ),
        onClick = onClick
    )
}

/**
 * Predefined spacing components following design system
 */
object PlantinSpacer {
    @Composable
    fun Horizontal(space: Dp) {
        Spacer(modifier = Modifier.width(space))
    }

    @Composable
    fun Vertical(space: Dp) {
        Spacer(modifier = Modifier.height(space))
    }

    // Predefined spacing values
    @Composable
    fun XSmall() = Vertical(PlantinTheme.spacing.xs)

    @Composable
    fun Small() = Vertical(PlantinTheme.spacing.sm)

    @Composable
    fun Medium() = Vertical(PlantinTheme.spacing.md)

    @Composable
    fun Large() = Vertical(PlantinTheme.spacing.lg)

}

/**
 * Extensions for common layout patterns
 */

/**
 * Standard screen padding using design system tokens
 */
@Stable
@Composable
fun Modifier.screenPadding(): Modifier {
    return this.padding(horizontal = PlantinTheme.spacing.lg)
}

/**
 * Card content padding following design system
 */
@Stable
@Composable
fun Modifier.cardPadding(): Modifier {
    return this.padding(PlantinTheme.spacing.md)
}
