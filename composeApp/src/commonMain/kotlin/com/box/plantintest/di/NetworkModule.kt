package com.box.plantintest.di

import com.box.plantintest.data.network.Network
import com.box.plantintest.data.remote.CharactersApi
import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module

val networkModule = module {
    single(named(Qualifiers.BASE_URL)) { "https://rickandmortyapi.com/api" }

    single {
        Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        }
    }

    single { Network(json = get()) }
    single<HttpClient> { get<Network>().httpClient }

    single {
        CharactersApi(
            httpClient = get(),
            baseUrl = get(named(Qualifiers.BASE_URL)),
        )
    }
}

