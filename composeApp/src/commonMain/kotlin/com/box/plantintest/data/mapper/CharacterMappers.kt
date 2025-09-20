package com.box.plantintest.data.mapper

import com.box.plantintest.data.remote.CharacterDto
import com.box.plantintest.data.remote.CharactersResponseDto
import com.box.plantintest.domain.model.Character
import com.box.plantintest.domain.model.CharacterGender
import com.box.plantintest.domain.model.CharacterStatus
import com.box.plantintest.domain.model.PagedCharacters
import com.box.plantintest.utils.extractNextPage

fun CharacterDto.toDomain(): Character = Character(
    id = id,
    name = name,
    status = CharacterStatus.from(status),
    species = species.orEmpty(),
    type = type.orEmpty(),
    gender = CharacterGender.from(gender),
    originName = origin.name,
    originUrl = origin.url,
    locationName = location.name,
    locationUrl = location.url,
    imageUrl = image,
    episodeUrls = episode,
    detailsUrl = url,
    createdAtIso = created,
)

fun CharactersResponseDto.toDomain(): PagedCharacters = PagedCharacters(
    items = results?.map { it.toDomain() } ?: emptyList(),
    nextPage = extractNextPage(info?.next),
    totalCount = info?.count ?: 0,
    totalPages = info?.pages ?: 0,
)

