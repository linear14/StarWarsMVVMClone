package com.ldh.starwars_mvvm_clone.di

import com.ldh.data_remote.repository.CharacterSearchRepository
import com.ldh.domain.repository.ICharacterSearchRepository
import org.koin.dsl.module

val remoteDataSourceModule = module {

    // 인터페이스를 타입 파라미터로 받네?
    single<ICharacterSearchRepository> { CharacterSearchRepository(apiService = get()) }
}