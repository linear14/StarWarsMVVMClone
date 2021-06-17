package com.ldh.starwars_mvvm_clone.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ldh.domain.usecases.SearchCharactersBaseUseCase
import com.ldh.domain.usecases.SearchCharactersUseCase
import com.ldh.starwars_mvvm_clone.commons.ExceptionHandler
import com.ldh.starwars_mvvm_clone.mappers.toPresentation
import com.ldh.starwars_mvvm_clone.models.CharacterPresentation
import com.ldh.starwars_mvvm_clone.models.states.DashboardSearchViewState
import com.ldh.starwars_mvvm_clone.models.states.Error
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect

internal class DashboardSearchViewModel(
    private val searchCharactersUseCase: SearchCharactersBaseUseCase
) : BaseViewModel() {

    private var searchJob: Job? = null

    val searchViewState: LiveData<DashboardSearchViewState>
        get() = _searchViewState

    private var _searchViewState = MutableLiveData<DashboardSearchViewState>()

    override val coroutineExceptionHandler =  CoroutineExceptionHandler { coroutineContext, throwable ->
        val message = ExceptionHandler.parse(throwable)
        onSearchError(message)
    }

    init {
        _searchViewState.value = DashboardSearchViewState(
            isLoading = false,
            error = null,
            searchResult = null
        )
    }

    override fun onCleared() {
        super.onCleared()
        searchJob?.cancel()
    }

    // region Public API

    fun executeCharacterSearch(characterName: String) {
        searchJob?.cancel()
        searchJob = launchCoroutine {
            delay(500)
            onSearchLoading()
            searchCharactersUseCase.invoke(characterName).collect { results ->
                val characters = results.map { it.toPresentation() }
                onSearchComplete(characters)
            }
        }
    }

    // endregion


    // region Private API

    private fun onSearchComplete(characters: List<CharacterPresentation>) {
        _searchViewState.value = _searchViewState.value?.copy(isLoading = false, searchResult = characters)
    }

    private fun onSearchLoading() {
        _searchViewState.value = _searchViewState.value?.copy(isLoading = false)
    }

    private fun onSearchError(message: Int) {
        _searchViewState.value = _searchViewState.value?.copy(isLoading = false, error = Error(message))
    }

    // endregion
}