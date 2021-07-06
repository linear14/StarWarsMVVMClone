package com.ldh.starwars_mvvm_clone.commons

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ldh.starwars_mvvm_clone.R

internal fun RecyclerView.initRecyclerViewWithLineDecoration(context: Context) {
    val linearLayoutManager = LinearLayoutManager(context)
    val itemDecoration = DividerItemDecoration(context, linearLayoutManager.orientation).apply {
        setDrawable(ContextCompat.getDrawable(context, R.drawable.line_divider)!!)
    }

    layoutManager = linearLayoutManager
    addItemDecoration(itemDecoration)
}