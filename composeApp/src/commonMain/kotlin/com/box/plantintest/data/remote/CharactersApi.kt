package com.box.plantintest.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class CharactersApi(
    private val httpClient: HttpClient,
    baseUrl: String,
) {
    private val apiBaseUrl: String = baseUrl.trimEnd('/')

    suspend fun getCharacters(
        page: Int,
        name: String?,
        status: String?,
        species: String?,
        type: String?,
        gender: String?,
    ): CharactersResponseDto {
        val url = "$apiBaseUrl/character"
        val response = httpClient.get(url) {
            parameter("page", page)

            name?.takeIf { it.isNotBlank() }?.let { parameter("name", it) }
            status?.takeIf { it.isNotBlank() }?.let { parameter("status", it) }
            species?.takeIf { it.isNotBlank() }?.let { parameter("species", it) }
            type?.takeIf { it.isNotBlank() }?.let { parameter("type", it) }
            gender?.takeIf { it.isNotBlank() }?.let { parameter("gender", it) }
        }
        return response.body()
    }

    suspend fun getCharacter(id: Int): CharacterDto {
        val url = "$apiBaseUrl/character/$id"
        val response = httpClient.get(url)
        return response.body()
    }
}

