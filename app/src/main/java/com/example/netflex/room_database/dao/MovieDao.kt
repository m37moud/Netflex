package com.example.netflex.room_database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.netflex.model.MovieEntity

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies WHERE id=:id")
    suspend fun findMovieById(id: Int): MovieEntity?

    @Insert
    suspend fun insertMovie(movieEntity: MovieEntity)

    @Delete
    suspend fun deleteMovie(movieEntity: MovieEntity)

    @Query("SELECT EXISTS(SELECT * FROM movies WHERE id=:id)")
    fun checkIsSaved(id: Int): Boolean
}