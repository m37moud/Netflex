package com.example.netflex.room_database.type_converters

import android.graphics.Bitmap
import androidx.room.TypeConverter
import com.example.netflex.utils.toBitmap
import com.example.netflex.utils.toByteArray

class PosterTypeConverters {
    @TypeConverter
    fun toBitmap(bytes: ByteArray): Bitmap = bytes.toBitmap()

    @TypeConverter
    fun fromBitmap(bmp: Bitmap): ByteArray = bmp.toByteArray()
}