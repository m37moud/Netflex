package com.example.netflex.repository

import com.example.netflex.retrofit.ApiResponse
import com.example.netflex.retrofit.MovieApi
import com.example.netflex.room_database.dao.MovieDao
import javax.inject.Inject

class MovieRepository @Inject constructor(private val movieDao: MovieDao,
                                          private val movieApi: MovieApi): Repository {
    override suspend fun fetchPopularMovies(page: Int): ApiResponse? {
        return movieApi.getPopularMovies(page)
    }

    override suspend fun fetchTopRatedMovies(page: Int): ApiResponse? {
        return movieApi.getTopRatedMovies(page)
    }

}