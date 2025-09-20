package com.box.plantintest.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

expect val platformModule: Module

object Qualifiers {
    const val DEFAULT_SCOPE = "DefaultScope"
    const val IO_SCOPE = "IoScope"
    const val MAIN_SCOPE = "MainScope"
    const val BASE_URL = "BaseURL"
}

val coroutinesModule = module {
    single(named(Qualifiers.DEFAULT_SCOPE)) { CoroutineScope(SupervisorJob() + Dispatchers.Default) }
    single(named(Qualifiers.IO_SCOPE)) { CoroutineScope(SupervisorJob() + Dispatchers.IO) }
    single(named(Qualifiers.MAIN_SCOPE)) { CoroutineScope(SupervisorJob() + Dispatchers.Main) }
}

val appModules: List<Module> = listOf(
    platformModule,
    coroutinesModule,
    networkModule,
    repositoryModule,
    useCaseModule,
    viewModelModule,
)
