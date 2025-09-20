package com.box.plantintest.presentation.common.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.box.plantintest.presentation.common.designsystem.PlantinTheme
import com.box.plantintest.presentation.common.designsystem.utils.screenPadding

@Composable
fun PLoadingListPlaceholder(
    modifier: Modifier = Modifier,
    itemCount: Int = 10
) {
    Column(
        modifier = modifier.screenPadding(),
        verticalArrangement = Arrangement.spacedBy(PlantinTheme.spacing.md)
    ) {
        repeat(itemCount) {
            PCharacterSkeletonLoader()
        }
    }
}
