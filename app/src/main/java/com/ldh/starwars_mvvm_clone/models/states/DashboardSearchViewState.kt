package com.ldh.starwars_mvvm_clone.models.states

import com.ldh.starwars_mvvm_clone.models.CharacterPresentation

internal data class DashboardSearchViewState(
    val isLoading: Boolean,
    val error: Error?,
    val searchResult: List<CharacterPresentation>?
)
