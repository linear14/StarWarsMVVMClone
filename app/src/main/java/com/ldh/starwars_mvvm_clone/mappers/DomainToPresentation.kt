package com.ldh.starwars_mvvm_clone.mappers

import com.ldh.domain.models.Character
import com.ldh.starwars_mvvm_clone.commons.convertToInches
import com.ldh.starwars_mvvm_clone.models.CharacterPresentation

internal fun Character.toPresentation(): CharacterPresentation {
    return CharacterPresentation(
        name,
        birthYear,
        height,
        convertToInches(height),
        url
    )
}