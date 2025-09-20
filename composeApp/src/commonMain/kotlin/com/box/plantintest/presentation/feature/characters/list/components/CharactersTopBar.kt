package com.box.plantintest.presentation.feature.characters.list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import com.box.plantintest.presentation.common.designsystem.PlantinTheme
import com.box.plantintest.presentation.common.designsystem.components.PTextFieldSearch
import com.box.plantintest.presentation.common.designsystem.components.PToolbar
import com.box.plantintest.presentation.common.designsystem.utils.screenPadding

@Stable
data class SearchState(
    val query: String,
    val onQueryChange: (String) -> Unit,
    val onSearch: () -> Unit
)

@Composable
fun CharactersTopBar(
    searchState: SearchState,
    onOpenFilters: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        PToolbar(
            title = "Characters",
            actions = {
                IconButton(onClick = onOpenFilters) {
                    Icon(
                        imageVector = Icons.Default.FilterList, 
                        contentDescription = "Filters"
                    )
                }
            }
        )
        
        PTextFieldSearch(
            value = searchState.query,
            onValueChange = searchState.onQueryChange,
            onSearch = searchState.onSearch,
            modifier = Modifier
                .screenPadding()
                .padding(bottom = PlantinTheme.spacing.md)
        )
    }
}
