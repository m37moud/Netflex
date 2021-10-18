package com.example.netflex.utils

import android.content.Context
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.example.netflex.R

fun loadPoster(context: Context, uri: String): RequestBuilder<Drawable> {
    return Glide
        .with(context)
        .load(uri)
        .centerCrop()
        .placeholder(R.color.black)
}