package com.example.netflex.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.netflex.R

fun ImageView.loadImage(context: Context, uri: String) {
    Glide
        .with(context)
        .load(uri)
        .centerCrop()
        .placeholder(R.color.black)
        .into(this)
}