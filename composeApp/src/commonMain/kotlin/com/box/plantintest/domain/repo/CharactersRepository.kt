package com.box.plantintest.domain.repo

import com.box.plantintest.domain.model.Character
import com.box.plantintest.domain.model.CharacterFilters
import com.box.plantintest.domain.model.PagedCharacters

interface CharactersRepository {
    suspend fun getCharacters(filters: CharacterFilters): PagedCharacters
    suspend fun getCharacterDetails(id: Int): Character
}
