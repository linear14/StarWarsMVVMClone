package com.ldh.starwars_mvvm_clone.di

import com.ldh.domain.repository.ICharacterSearchRepository
import com.ldh.domain.usecases.SearchCharactersBaseUseCase
import com.ldh.domain.usecases.SearchCharactersUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

val useCasesModule = module {

    single(named("search")) { provideSearchUseCase(get()) }
}

fun provideSearchUseCase(searchRepository: ICharacterSearchRepository): SearchCharactersBaseUseCase {
    return SearchCharactersUseCase(searchRepository)
}

