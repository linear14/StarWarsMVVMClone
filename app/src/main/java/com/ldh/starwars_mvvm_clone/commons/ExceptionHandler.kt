package com.ldh.starwars_mvvm_clone.commons

import androidx.annotation.StringRes
import com.ldh.starwars_mvvm_clone.R
import java.net.UnknownHostException

internal object ExceptionHandler {

    @StringRes
    fun parse(t: Throwable): Int {
        return when(t) {
            is UnknownHostException -> R.string.error_check_internet_connection
            else -> R.string.error_oops_error_occured
        }
    }
}