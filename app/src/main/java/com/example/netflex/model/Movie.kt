package com.example.netflex.model

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.netflex.retrofit.RetrofitConstants
import java.io.Serializable

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    val overview: String,
    val original_title: String,
    val vote_average: Double,
    val release_date: String,
    var poster: Bitmap? = null,
    val poster_path: String? = null
): Serializable{
    fun generateImageUrl(): String{
        return RetrofitConstants.IMAGE_BASE_URL + poster_path
    }
}
