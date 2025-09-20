package com.box.plantintest.presentation.feature.characters.list.components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.box.plantintest.domain.model.Character
import com.box.plantintest.presentation.common.designsystem.PlantinTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.distinctUntilChanged

@Stable
data class CharactersListData(
    val items: ImmutableList<Character>,
    val isLoadingMore: Boolean,
    val canLoadMore: Boolean,
)

@Composable
fun CharactersList(
    data: CharactersListData,
    onItemClick: (Int) -> Unit,
    onLoadMore: () -> Unit,
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
) {
    // Optimized pagination trigger using snapshotFlow
    val shouldLoadMore by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val totalItems = layoutInfo.totalItemsCount
            val lastVisibleIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            
            totalItems > 0 && 
            lastVisibleIndex >= totalItems - 3 && // Trigger when 3 items from end
            data.canLoadMore && 
            !data.isLoadingMore
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow { shouldLoadMore }
            .distinctUntilChanged()
            .collect { shouldLoad ->
                if (shouldLoad) {
                    onLoadMore()
                }
            }
    }

    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            horizontal = PlantinTheme.spacing.lg,
            vertical = PlantinTheme.spacing.md,
        ),
        verticalArrangement = Arrangement.spacedBy(PlantinTheme.spacing.md),
    ) {
        items(
            items = data.items,
            key = { character -> character.id }
        ) { character ->
            CharacterItemCard(
                character = character,
                onClick = { onItemClick(character.id) },
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItem(
                        fadeInSpec = tween(300),
                        fadeOutSpec = tween(300)
                    )
            )
        }

        if (data.isLoadingMore) {
            item(key = "loading_more") {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = PlantinTheme.spacing.md),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
