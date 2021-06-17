package com.ldh.starwars_mvvm_clone.commons

import java.math.BigDecimal
import java.math.RoundingMode

internal fun convertToInches(centimeters: String): String {
    return (BigDecimal(centimeters.toDouble() * 0.393701).setScale(3, RoundingMode.HALF_EVEN)).toString()
}