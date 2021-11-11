package com.example.netflex.repository

import com.example.netflex.model.Movie
import com.example.netflex.retrofit.ApiResponse
import com.example.netflex.retrofit.MovieApi
import com.example.netflex.room_database.dao.MovieDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(private val movieDao: MovieDao,
                                          private val movieApi: MovieApi): Repository {
    override suspend fun fetchPopularMovies(page: Int) = movieApi.getPopularMovies(page)

    override suspend fun fetchTopRatedMovies(page: Int) = movieApi.getTopRatedMovies(page)

    override suspend fun findMovieById(id: Int) = movieDao.findMovieById(id)

    override suspend fun insertMovieToDb(movie: Movie) = movieDao.insertMovie(movie)

    override suspend fun deleteMovie(movie: Movie) = movieDao.deleteMovie(movie)

    override suspend fun checkIsSaved(id: Int): Boolean = movieDao.checkIsSaved(id)
}