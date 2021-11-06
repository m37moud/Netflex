package com.example.netflex.utils

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils

fun View.executeAnimation(context: Context, animation: Int, duration: Long) {
    val anim = AnimationUtils.loadAnimation(context, animation)
    anim.duration = duration
    this.startAnimation(anim)
}