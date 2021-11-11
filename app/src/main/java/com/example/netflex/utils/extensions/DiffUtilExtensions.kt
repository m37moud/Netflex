package com.example.netflex.utils.extensions

import androidx.recyclerview.widget.DiffUtil
import com.example.netflex.ui.adapter.MovieRecyclerAdapter


fun DiffUtil.Callback.setDataToAdapter(adapter: MovieRecyclerAdapter){
    val results = DiffUtil.calculateDiff(this)
    results.dispatchUpdatesTo(adapter)
}