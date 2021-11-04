package com.example.netflex.room_database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.netflex.model.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies WHERE id=:id")
    suspend fun findMovieById(id: Int): Movie?

    @Insert
    suspend fun insertMovie(movie: Movie)

    @Delete
    suspend fun deleteMovie(movie: Movie)

    @Query("SELECT EXISTS(SELECT * FROM movies WHERE id=:id)")
    fun checkIsSaved(id: Int): Boolean
}