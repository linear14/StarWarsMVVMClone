package com.ldh.starwars_mvvm_clone.activities

import android.os.Bundle
import com.ldh.starwars_mvvm_clone.R
import com.ldh.starwars_mvvm_clone.base.BaseActivity

internal class DashboardActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
    }
}