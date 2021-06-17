package com.ldh.starwars_mvvm_clone.di

import com.ldh.domain.repository.ICharacterSearchRepository
import com.ldh.domain.usecases.SearchCharactersBaseUseCase
import com.ldh.domain.usecases.SearchCharactersUseCase
import com.ldh.starwars_mvvm_clone.viewmodel.DashboardSearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelsModule = module {

    viewModel { DashboardSearchViewModel(get(named("search"))) }
}


