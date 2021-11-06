package com.example.netflex.utils

import android.graphics.BitmapFactory

fun ByteArray.toBitmap() = BitmapFactory.decodeByteArray(this, 0, this.size)!!