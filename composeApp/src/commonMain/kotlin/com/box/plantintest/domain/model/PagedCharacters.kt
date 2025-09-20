package com.box.plantintest.domain.model

data class PagedCharacters(
    val items: List<Character>,
    val nextPage: Int?,
    val totalCount: Int,
    val totalPages: Int,
)
