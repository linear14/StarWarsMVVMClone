package com.ldh.starwars_mvvm_clone.models.states

import androidx.annotation.StringRes

// 에러를 따로 만들어서 관리하는구나
internal data class Error(@StringRes val message: Int)
