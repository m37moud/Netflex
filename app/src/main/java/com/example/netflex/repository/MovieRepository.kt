package com.example.netflex.repository

import com.example.netflex.model.MovieEntity
import com.example.netflex.retrofit.ApiResponse
import com.example.netflex.retrofit.MovieApi
import com.example.netflex.room_database.dao.MovieDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(private val movieDao: MovieDao,
                                          private val movieApi: MovieApi): Repository {
    override suspend fun fetchPopularMovies(page: Int): ApiResponse? {
        return movieApi.getPopularMovies(page)
    }

    override suspend fun fetchTopRatedMovies(page: Int) = movieApi.getTopRatedMovies(page)

    override suspend fun findMovieById(id: Int) = movieDao.findMovieById(id)

    override suspend fun insertMovieToDb(movieEntity: MovieEntity) = movieDao.insertMovie(movieEntity)

    override suspend fun deleteMovie(movieEntity: MovieEntity) = movieDao.deleteMovie(movieEntity)

    override suspend fun checkIsSaved(id: Int): Boolean = movieDao.checkIsSaved(id)
}