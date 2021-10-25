package com.example.netflex.model

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.netflex.utils.RetrofitConstants

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
){
    fun generateImageUrl(): String{
        return RetrofitConstants.IMAGE_BASE_URL + poster_path
    }
}
