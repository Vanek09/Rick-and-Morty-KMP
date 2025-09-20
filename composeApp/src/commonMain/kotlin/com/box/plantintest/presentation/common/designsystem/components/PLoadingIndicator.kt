package com.box.plantintest.presentation.common.designsystem.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.box.plantintest.presentation.common.designsystem.PlantinTheme

/**
 * Loading indicator sizes
 */
@Immutable
enum class PLoadingSize {
    Small,      // 16dp
    Medium,     // 24dp  
    Large,      // 32dp
    XLarge,     // 48dp
}

/**
 * Circular loading indicator with consistent styling
 */
@Composable
fun PCircularLoadingIndicator(
    modifier: Modifier = Modifier,
    size: PLoadingSize = PLoadingSize.Medium,
) {
    val indicatorSize = when (size) {
        PLoadingSize.Small -> 16.dp
        PLoadingSize.Medium -> 24.dp
        PLoadingSize.Large -> 32.dp
        PLoadingSize.XLarge -> 48.dp
    }
    
    val strokeWidth = when (size) {
        PLoadingSize.Small -> 2.dp
        PLoadingSize.Medium -> 3.dp
        PLoadingSize.Large -> 4.dp
        PLoadingSize.XLarge -> 5.dp
    }

    CircularProgressIndicator(
        modifier = modifier.size(indicatorSize),
        color = PlantinTheme.colors.primary,
        strokeWidth = strokeWidth,
        strokeCap = StrokeCap.Round
    )
}


/**
 * Skeleton loader for character list items
 */
@Composable
fun PCharacterSkeletonLoader(
    modifier: Modifier = Modifier,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "skeleton")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    PCard(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .alpha(alpha),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar skeleton
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(PlantinTheme.colors.surfaceVariant)
            )
            
            Spacer(modifier = Modifier.width(PlantinTheme.spacing.md))
            
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(PlantinTheme.spacing.xs)
            ) {
                // Title skeleton
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(16.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(PlantinTheme.colors.surfaceVariant)
                )
                
                // Status chip skeleton
                Box(
                    modifier = Modifier
                        .width(80.dp)
                        .height(24.dp)
                        .clip(RoundedCornerShape(PlantinTheme.radius.chip))
                        .background(PlantinTheme.colors.surfaceVariant)
                )
                
                // Subtitle skeleton
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(12.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(PlantinTheme.colors.surfaceVariant)
                )
            }
        }
    }
}


