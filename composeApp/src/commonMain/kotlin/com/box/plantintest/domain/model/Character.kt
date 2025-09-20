package com.box.plantintest.domain.model

data class Character(
    val id: Int,
    val name: String,
    val status: CharacterStatus,
    val species: String,
    val type: String,
    val gender: CharacterGender,
    val originName: String,
    val originUrl: String,
    val locationName: String,
    val locationUrl: String,
    val imageUrl: String,
    val episodeUrls: List<String>,
    val detailsUrl: String,
    val createdAtIso: String,
)
