package com.example.netflex.room_database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.netflex.model.MovieEntity
import com.example.netflex.room_database.dao.MovieDao
import com.example.netflex.room_database.type_converters.PosterTypeConverters

@TypeConverters(PosterTypeConverters::class)
@Database(entities = [MovieEntity::class], version = 1, exportSchema = false) // TODO: Configure db to be singleton using di
abstract class MovieDataBase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}