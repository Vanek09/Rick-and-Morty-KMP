package com.box.plantintest.data.repo

import com.box.plantintest.data.mapper.toDomain
import com.box.plantintest.data.remote.CharactersApi
import com.box.plantintest.domain.model.Character
import com.box.plantintest.domain.model.CharacterFilters
import com.box.plantintest.domain.model.CharacterGender
import com.box.plantintest.domain.model.CharacterStatus
import com.box.plantintest.domain.model.PagedCharacters
import com.box.plantintest.domain.repo.CharactersRepository

class CharactersRepositoryImpl(
    private val api: CharactersApi,
) : CharactersRepository {

    override suspend fun getCharacters(filters: CharacterFilters): PagedCharacters {
        val response = api.getCharacters(
            page = filters.page,
            name = filters.name,
            status = filters.status?.toApiParam(),
            species = filters.species,
            type = filters.type,
            gender = filters.gender?.toApiParam(),
        )

        return response.toDomain()
    }

    override suspend fun getCharacterDetails(id: Int): Character {
        return api.getCharacter(id).toDomain()
    }
}

private fun CharacterStatus.toApiParam(): String = when (this) {
    CharacterStatus.Alive -> "alive"
    CharacterStatus.Dead -> "dead"
    CharacterStatus.Unknown -> "unknown"
}

private fun CharacterGender.toApiParam(): String = when (this) {
    CharacterGender.Female -> "female"
    CharacterGender.Male -> "male"
    CharacterGender.Genderless -> "genderless"
    CharacterGender.Unknown -> "unknown"
}

