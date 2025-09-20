package com.box.plantintest.presentation.feature.characters.list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.box.plantintest.domain.model.CharacterFilters
import com.box.plantintest.domain.model.CharacterGender
import com.box.plantintest.domain.model.CharacterSort
import com.box.plantintest.domain.model.CharacterStatus
import com.box.plantintest.presentation.common.designsystem.PlantinTheme
import com.box.plantintest.presentation.common.designsystem.components.PButton
import com.box.plantintest.presentation.common.designsystem.components.PButtonState
import com.box.plantintest.presentation.common.designsystem.components.PButtonVariant
import com.box.plantintest.presentation.common.designsystem.components.PTextFieldSearch

@Immutable
data class FilterSheetState(
    val filters: CharacterFilters,
    val sort: CharacterSort,
    val isVisible: Boolean
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersFilterSheet(
    state: FilterSheetState,
    onFiltersChange: (CharacterFilters) -> Unit,
    onSortChange: (CharacterSort) -> Unit,
    onApply: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (!state.isVisible) return

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        modifier = modifier,
    ) {
        Column(modifier = Modifier.padding(PlantinTheme.spacing.lg)) {
            Text(
                text = "Filters",
                style = PlantinTheme.typography.titleLarge,
                color = PlantinTheme.colors.onSurface
            )
            Spacer(Modifier.height(PlantinTheme.spacing.md))

            FilterSection(
                title = "Status",
                content = {
                    StatusChips(
                        selected = state.filters.status,
                        onSelected = { status ->
                            onFiltersChange(state.filters.copy(status = status))
                        }
                    )
                }
            )

            FilterSection(
                title = "Gender",
                content = {
                    GenderChips(
                        selected = state.filters.gender,
                        onSelected = { gender ->
                            onFiltersChange(state.filters.copy(gender = gender))
                        }
                    )
                }
            )

            FilterSection(
                title = "Species",
                content = {
                    PTextFieldSearch(
                        value = state.filters.species.orEmpty(),
                        onValueChange = { species ->
                            onFiltersChange(state.filters.copy(species = species.ifBlank { null }))
                        },
                        onSearch = {}
                    )
                }
            )

            FilterSection(
                title = "Type",
                content = {
                    PTextFieldSearch(
                        value = state.filters.type.orEmpty(),
                        onValueChange = { type ->
                            onFiltersChange(state.filters.copy(type = type.ifBlank { null }))
                        },
                        onSearch = {}
                    )
                }
            )

            FilterSection(
                title = "Sort",
                content = {
                    SortOptions(
                        selected = state.sort,
                        onSelected = onSortChange
                    )
                }
            )

            Spacer(Modifier.height(PlantinTheme.spacing.lg))
            PButton(
                state = PButtonState(text = "Apply"),
                onClick = onApply,
                variant = PButtonVariant.Primary
            )
            Spacer(Modifier.height(PlantinTheme.spacing.md))
        }
    }
}

@Composable
private fun FilterSection(
    title: String,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = PlantinTheme.typography.label,
            color = PlantinTheme.colors.onSurface
        )
        Spacer(Modifier.height(PlantinTheme.spacing.sm))
        content()
        Spacer(Modifier.height(PlantinTheme.spacing.md))
    }
}

@Composable
private fun StatusChips(
    selected: CharacterStatus?,
    onSelected: (CharacterStatus?) -> Unit,
    modifier: Modifier = Modifier,
) {
    val options = remember {
        listOf(
            CharacterStatus.Alive,
            CharacterStatus.Dead,
            CharacterStatus.Unknown,
        )
    }

    Row(modifier = modifier) {
        options.forEach { status ->
            val isSelected = selected == status
            FilterChip(
                selected = isSelected,
                onClick = { onSelected(if (isSelected) null else status) },
                label = { Text(status.name) },
                modifier = Modifier.padding(end = PlantinTheme.spacing.sm)
            )
        }
    }
}

@Composable
private fun GenderChips(
    selected: CharacterGender?,
    onSelected: (CharacterGender?) -> Unit,
    modifier: Modifier = Modifier,
) {
    val options = remember {
        listOf(
            CharacterGender.Male,
            CharacterGender.Female,
            CharacterGender.Genderless,
            CharacterGender.Unknown,
        )
    }

    Row(modifier = modifier) {
        options.forEach { gender ->
            val isSelected = selected == gender
            PButton(
                onClick = { onSelected(if (isSelected) null else gender) },
                text = gender.name,
                primary = isSelected,
                modifier = Modifier.padding(end = PlantinTheme.spacing.sm)
            )
        }
    }
}

@Composable
private fun SortOptions(
    selected: CharacterSort,
    onSelected: (CharacterSort) -> Unit,
    modifier: Modifier = Modifier,
) {
    val options = remember {
        listOf(
            CharacterSort.NameAsc to "Name ↑",
            CharacterSort.NameDesc to "Name ↓",
            CharacterSort.StatusAsc to "Status",
            CharacterSort.SpeciesAsc to "Species",
        )
    }

    Row(modifier = modifier) {
        options.forEach { (sort, label) ->
            PButton(
                onClick = { onSelected(sort) },
                text = label,
                primary = selected == sort,
                modifier = Modifier.padding(end = PlantinTheme.spacing.sm)
            )
        }
    }
}
