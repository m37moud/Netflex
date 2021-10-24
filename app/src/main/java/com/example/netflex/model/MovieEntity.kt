package com.example.netflex.model

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val overview: String,
    val original_title: String,
    val vote_average: Double,
    val release_date: String,
    val poster: Bitmap? = null,
    val poster_path: String? = null
)
