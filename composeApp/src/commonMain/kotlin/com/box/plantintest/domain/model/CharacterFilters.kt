package com.box.plantintest.domain.model

enum class CharacterSort {
    None,
    NameAsc,
    NameDesc,
    CreatedAsc,
    CreatedDesc,
    StatusAsc,
    SpeciesAsc,
}

data class CharacterFilters(
    val page: Int = 1,
    val name: String? = null,
    val status: CharacterStatus? = null,
    val species: String? = null,
    val type: String? = null,
    val gender: CharacterGender? = null,
    val sort: CharacterSort = CharacterSort.None,
)

