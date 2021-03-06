package com.example.netflex.room_database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.netflex.model.Movie
import com.example.netflex.room_database.dao.MovieDao
import com.example.netflex.room_database.type_converters.PosterTypeConverters

@TypeConverters(PosterTypeConverters::class)
@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MovieDataBase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}