package com.ldh.domain.repository

import com.ldh.domain.models.Character
import kotlinx.coroutines.flow.Flow

interface ICharacterSearchRepository {
    suspend fun searchCharacters(characterName: String): Flow<List<Character>>
}