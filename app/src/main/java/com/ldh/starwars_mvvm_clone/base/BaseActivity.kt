package com.ldh.starwars_mvvm_clone.base

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import androidx.appcompat.app.AppCompatActivity
import com.ldh.starwars_mvvm_clone.commons.versionFrom

// internal : 같은 모듈 내에서 접근 가능
internal open class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        whiteStatusBar()
    }

    @Suppress("DEPRECATION")
    private fun whiteStatusBar() {
        if(versionFrom(Build.VERSION_CODES.R)) {
            window.decorView.windowInsetsController?.setSystemBarsAppearance(APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS)
            window.statusBarColor = getColor(android.R.color.white)
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = getColor(android.R.color.white)
        }
    }
}