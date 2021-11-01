package com.example.netflex.adapter.scroll_listener

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager

class RecyclerScrollListener(
    private val manager: GridLayoutManager,
    private val callback: () -> Unit
): View.OnScrollChangeListener {

    override fun onScrollChange(
        v: View?,
        scrollX: Int,
        scrollY: Int,
        oldScrollX: Int,
        oldScrollY: Int
    ) {
        val visibleItems = manager.childCount
        val totalItems = manager.itemCount
        val firstVisible = manager.findFirstVisibleItemPosition()
        if (firstVisible + visibleItems >= totalItems){
            callback()
        }
    }
}