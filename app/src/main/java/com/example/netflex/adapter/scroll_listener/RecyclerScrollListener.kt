package com.example.netflex.adapter.scroll_listener

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerScrollListener(
    private val manager: GridLayoutManager,
    private val callback: () -> Unit
): RecyclerView.OnScrollListener() {

    private var previousDataCount = manager.itemCount
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (manager.findFirstVisibleItemPosition() + manager.childCount >= manager.itemCount
            && previousDataCount != manager.itemCount){
            callback()
            previousDataCount = manager.itemCount
        }
    }

}