package com.example.netflex.ui.adapter.scroll_listener

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager

class RecyclerScrollListener(
    private val manager: GridLayoutManager,
    private val callback: () -> Unit
): View.OnScrollChangeListener {
    private var previousDataCount = manager.itemCount

    override fun onScrollChange(
        v: View?,
        scrollX: Int,
        scrollY: Int,
        oldScrollX: Int,
        oldScrollY: Int
    ) {
        if (manager.findFirstVisibleItemPosition() + manager.childCount >= manager.itemCount
            && previousDataCount != manager.itemCount){
            callback()
            previousDataCount = manager.itemCount
        }
    }
}