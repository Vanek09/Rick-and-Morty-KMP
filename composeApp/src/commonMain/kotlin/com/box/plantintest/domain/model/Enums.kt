package com.box.plantintest.domain.model

enum class CharacterStatus {
    Alive,
    Dead,
    Unknown;

    companion object {
        fun from(value: String?): CharacterStatus = when (value?.lowercase()) {
            "alive" -> Alive
            "dead" -> Dead
            else -> Unknown
        }
    }
}

enum class CharacterGender {
    Female,
    Male,
    Genderless,
    Unknown;

    companion object {
        fun from(value: String?): CharacterGender = when (value?.lowercase()) {
            "female" -> Female
            "male" -> Male
            "genderless" -> Genderless
            else -> Unknown
        }
    }
}

