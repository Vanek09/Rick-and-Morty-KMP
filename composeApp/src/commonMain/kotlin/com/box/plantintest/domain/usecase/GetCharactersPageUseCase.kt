package com.box.plantintest.domain.usecase

import com.box.plantintest.domain.model.CharacterFilters
import com.box.plantintest.domain.model.PagedCharacters
import com.box.plantintest.domain.repo.CharactersRepository
import com.box.plantintest.utils.emitFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetCharactersPageUseCase(
    private val repository: CharactersRepository,
) {
    operator fun invoke(
        query: String?,
        filters: CharacterFilters,
        page: Int,
    ): Flow<PagedCharacters> = emitFlow {
        val effectiveFilters = filters.copy(
            page = page,
            name = query?.takeIf { it.isNotBlank() } ?: filters.name,
        )
        repository.getCharacters(effectiveFilters)
    }.flowOn(Dispatchers.IO)
}
