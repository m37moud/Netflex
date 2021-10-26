package com.example.netflex.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.netflex.R
import java.io.ByteArrayOutputStream

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

fun Bitmap.toByteArray(): ByteArray {
    val outputStream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
    return outputStream.toByteArray()
}

fun ByteArray.toBitmap() = BitmapFactory.decodeByteArray(this, 0, this.size)!!