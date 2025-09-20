package com.box.plantintest.di

import com.box.plantintest.presentation.feature.characters.details.CharacterDetailsViewModel
import com.box.plantintest.presentation.feature.characters.list.CharactersListViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { CharactersListViewModel(getCharactersPage = get()) }
    factory { CharacterDetailsViewModel(getCharacterDetails = get()) }
}

