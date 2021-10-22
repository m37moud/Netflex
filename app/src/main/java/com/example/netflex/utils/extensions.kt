package com.example.netflex.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.example.netflex.R

fun View.executeAnimation(context: Context, animation: Int, duration: Long) {
    val anim = AnimationUtils.loadAnimation(context, animation)
    anim.duration = duration
    this.startAnimation(anim)
}

fun ImageView.loadImage(context: Context, uri: String) {
    Glide
        .with(context)
        .load(uri)
        .centerCrop()
        .placeholder(R.color.black)
        .into(this)
}