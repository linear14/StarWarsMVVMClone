package com.ldh.domain.usecases

import com.ldh.domain.models.Character
import com.ldh.domain.repository.ICharacterSearchRepository
import kotlinx.coroutines.flow.Flow

/*
    typealias: 타입의 이름이 너무 길고, 무엇을 나타내는지 잘 모르겠어서
    SearchCharactersBaseUseCase 별명으로 하나 만들었다.
    (새로운 타입을 만드는게 아니라, 단순 별칭이라고 생각하면 됨 (수학으로 치면 치환..!?))
 */
typealias SearchCharactersBaseUseCase = BaseUseCase<String, Flow<List<Character>>>

class SearchCharactersUseCase(
    private val searchRepository: ICharacterSearchRepository
): SearchCharactersBaseUseCase {
    override suspend operator fun invoke(params: String): Flow<List<Character>> {
        return searchRepository.searchCharacters(params)
    }
}