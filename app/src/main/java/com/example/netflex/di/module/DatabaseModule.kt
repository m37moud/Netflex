package com.example.netflex.di.module

import androidx.room.Room
import com.example.netflex.app.MyApp
import com.example.netflex.room_database.MovieDataBase
import com.example.netflex.room_database.dao.MovieDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDB(app: MyApp): MovieDataBase {
        return Room.databaseBuilder(
            app,
            MovieDataBase::class.java,
            "movies_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMovieDao(db: MovieDataBase): MovieDao{
        return db.movieDao()
    }
}