package com.box.plantintest.domain.usecase

import com.box.plantintest.domain.model.Character
import com.box.plantintest.domain.repo.CharactersRepository
import com.box.plantintest.utils.emitFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetCharacterDetailsUseCase(
    private val repository: CharactersRepository,
) {
    operator fun invoke(id: Int): Flow<Character> = emitFlow {
        repository.getCharacterDetails(id)
    }.flowOn(Dispatchers.IO)
}
