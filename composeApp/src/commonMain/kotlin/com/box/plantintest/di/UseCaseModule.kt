package com.box.plantintest.di

import com.box.plantintest.domain.usecase.GetCharacterDetailsUseCase
import com.box.plantintest.domain.usecase.GetCharactersPageUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetCharactersPageUseCase(repository = get()) }
    factory { GetCharacterDetailsUseCase(repository = get()) }
}

