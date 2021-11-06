package com.example.netflex.utils

import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.Glide

fun String.getImageAsBitmap(context: Context): Bitmap? {
    return Glide.with(context)
        .asBitmap()
        .load(this)
        .submit()
        .get()
}