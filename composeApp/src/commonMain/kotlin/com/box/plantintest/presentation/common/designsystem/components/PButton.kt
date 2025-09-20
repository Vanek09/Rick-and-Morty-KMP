package com.box.plantintest.presentation.common.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.box.plantintest.presentation.common.designsystem.PlantinTheme

/**
 * Button variants following Material 3 guidelines
 */
@Immutable
enum class PButtonVariant {
    Primary,    // Filled button - primary actions
    Secondary,  // Outlined button - secondary actions  
    Tertiary,   // Text button - low emphasis actions
}

/**
 * Button sizes for different use cases
 */
@Immutable
enum class PButtonSize {
    Small,      // Compact spaces
    Medium,     // Default size
    Large,      // Prominent actions
}

/**
 * Stable data class for button configuration
 */
@Stable
@Immutable
data class PButtonState(
    val text: String,
    val isEnabled: Boolean = true,
    val isLoading: Boolean = false,
    val leadingIcon: ImageVector? = null,
    val trailingIcon: ImageVector? = null,
)

/**
 * Primary button component following design system guidelines
 * 
 * @param state Button configuration and content
 * @param onClick Click handler
 * @param modifier Modifier for styling
 * @param variant Visual variant of the button
 * @param size Size variant of the button
 */
@Composable
fun PButton(
    state: PButtonState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: PButtonVariant = PButtonVariant.Primary,
    size: PButtonSize = PButtonSize.Medium,
) {
    val shape = RoundedCornerShape(PlantinTheme.radius.button)
    val contentPadding = when (size) {
        PButtonSize.Small -> PaddingValues(
            horizontal = PlantinTheme.spacing.md,
            vertical = PlantinTheme.spacing.sm
        )
        PButtonSize.Medium -> PaddingValues(
            horizontal = PlantinTheme.spacing.lg,
            vertical = PlantinTheme.spacing.md
        )
        PButtonSize.Large -> PaddingValues(
            horizontal = PlantinTheme.spacing.xl,
            vertical = PlantinTheme.spacing.lg
        )
    }
    
    val buttonModifier = modifier.then(
        when (size) {
            PButtonSize.Small -> Modifier.height(32.dp)
            PButtonSize.Medium -> Modifier.height(PlantinTheme.sizes.buttonHeight)
            PButtonSize.Large -> Modifier.height(56.dp)
        }
    )

    when (variant) {
        PButtonVariant.Primary -> {
            Button(
                onClick = onClick,
                modifier = buttonModifier,
                enabled = state.isEnabled && !state.isLoading,
                shape = shape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = PlantinTheme.colors.primary,
                    contentColor = PlantinTheme.colors.onPrimary,
                    disabledContainerColor = PlantinTheme.colors.outline,
                    disabledContentColor = PlantinTheme.colors.onSurfaceVariant,
                ),
                contentPadding = contentPadding,
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = PlantinTheme.elevation.button
                ),
            ) {
                PButtonContent(state = state, size = size)
            }
        }
        
        PButtonVariant.Secondary -> {
            OutlinedButton(
                onClick = onClick,
                modifier = buttonModifier,
                enabled = state.isEnabled && !state.isLoading,
                shape = shape,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = PlantinTheme.colors.primary,
                    disabledContentColor = PlantinTheme.colors.onSurfaceVariant,
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = if (state.isEnabled && !state.isLoading) {
                        PlantinTheme.colors.outline
                    } else {
                        PlantinTheme.colors.outlineVariant
                    }
                ),
                contentPadding = contentPadding,
            ) {
                PButtonContent(state = state, size = size)
            }
        }
        
        PButtonVariant.Tertiary -> {
            TextButton(
                onClick = onClick,
                modifier = buttonModifier,
                enabled = state.isEnabled && !state.isLoading,
                shape = shape,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = PlantinTheme.colors.primary,
                    disabledContentColor = PlantinTheme.colors.onSurfaceVariant,
                ),
                contentPadding = contentPadding,
            ) {
                PButtonContent(state = state, size = size)
            }
        }
    }
}

/**
 * Convenience function for primary buttons (backwards compatibility)
 */
@Composable
fun PButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    primary: Boolean = true,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
) {
    PButton(
        state = PButtonState(
            text = text,
            isEnabled = enabled,
            isLoading = isLoading,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
        ),
        onClick = onClick,
        modifier = modifier,
        variant = if (primary) PButtonVariant.Primary else PButtonVariant.Secondary,
    )
}

/**
 * Icon-only button for actions with clear iconography
 */
@Composable
fun PIconButton(
    onClick: () -> Unit,
    icon: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    size: PButtonSize = PButtonSize.Medium,
) {
    val iconSize = when (size) {
        PButtonSize.Small -> PlantinTheme.sizes.iconSmall
        PButtonSize.Medium -> PlantinTheme.sizes.iconMedium
        PButtonSize.Large -> PlantinTheme.sizes.iconLarge
    }
    
    IconButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = PlantinTheme.colors.onSurface,
            modifier = Modifier.size(iconSize)
        )
    }
}

@Composable
private fun PButtonContent(
    state: PButtonState,
    size: PButtonSize,
) {
    val iconSize = when (size) {
        PButtonSize.Small -> 16.dp
        PButtonSize.Medium -> 18.dp
        PButtonSize.Large -> 20.dp
    }
    
    val textStyle = when (size) {
        PButtonSize.Small -> PlantinTheme.typography.labelMedium
        PButtonSize.Medium -> PlantinTheme.typography.labelLarge
        PButtonSize.Large -> PlantinTheme.typography.titleMedium
    }

    if (state.isLoading) {
        CircularProgressIndicator(
            modifier = Modifier.size(iconSize),
            strokeWidth = 2.dp
        )
    } else {
        // Leading icon
        state.leadingIcon?.let { icon ->
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(iconSize)
            )
        }
        
        // Text
        Text(
            text = state.text,
            style = textStyle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        
        // Trailing icon
        state.trailingIcon?.let { icon ->
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(iconSize)
            )
        }
    }
}
