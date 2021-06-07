package com.ldh.data_remote.repository

import com.ldh.data_remote.api.StarWarsApiService
import com.ldh.data_remote.mappers.toDomain
import com.ldh.domain.models.Character
import com.ldh.domain.repository.ICharacterSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CharacterSearchRepository(
    private val apiService: StarWarsApiService
): ICharacterSearchRepository {

    // Flow 내부에는 join 같은 대기 코드가 없네? 공부 해보고 싶다.
    // domain 에서는 인터페이스를 제공하였음. 따라서, domain에서 사용 가능한 형태로 만들어주기 위해 converter가 필요함
    override suspend fun searchCharacters(characterName: String): Flow<List<Character>> = flow {
        val searchResponse = apiService.searchCharacters(characterName)
        val starWarsCharacters = mutableListOf<Character>()
        for(starWarsCharacter in searchResponse.results) {
            starWarsCharacters.add(starWarsCharacter.toDomain())
        }
        emit(starWarsCharacters)
    }
}