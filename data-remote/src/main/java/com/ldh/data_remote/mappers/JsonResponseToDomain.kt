package com.ldh.data_remote.mappers

import com.ldh.domain.models.Character
import com.ldh.data_remote.models.CharacterResponse

internal fun CharacterResponse.toDomain(): Character {
    return Character(this.name, this.birthYear, this.height, this.url)
}