package com.ldh.starwars_mvvm_clone.commons

import android.os.Build
import androidx.annotation.IntRange


private const val minVersion = Build.VERSION_CODES.M.toLong()
private const val maxVersion = Build.VERSION_CODES.R.toLong()

fun versionFrom(@IntRange(from = minVersion, to = maxVersion) versionCode: Int): Boolean =
     Build.VERSION.SDK_INT >= versionCode