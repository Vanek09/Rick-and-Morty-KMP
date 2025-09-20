package com.box.plantintest.di

import com.box.plantintest.data.repo.CharactersRepositoryImpl
import com.box.plantintest.domain.repo.CharactersRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<CharactersRepository> { CharactersRepositoryImpl(api = get()) }
}

