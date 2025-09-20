package com.box.plantintest.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharactersResponseDto(
    @SerialName("info") val info: PageInfoDto? = null,
    @SerialName("results") val results: List<CharacterDto>? = null,
)

@Serializable
data class PageInfoDto(
    @SerialName("count") val count: Int,
    @SerialName("pages") val pages: Int,
    @SerialName("next") val next: String?,
    @SerialName("prev") val prev: String?,
)

@Serializable
data class CharacterDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("status") val status: String,
    @SerialName("species") val species: String? = null,
    @SerialName("type") val type: String? = null,
    @SerialName("gender") val gender: String,
    @SerialName("origin") val origin: LocationRefDto,
    @SerialName("location") val location: LocationRefDto,
    @SerialName("image") val image: String,
    @SerialName("episode") val episode: List<String> = emptyList(),
    @SerialName("url") val url: String,
    @SerialName("created") val created: String,
)

@Serializable
data class LocationRefDto(
    @SerialName("name") val name: String,
    @SerialName("url") val url: String,
)


