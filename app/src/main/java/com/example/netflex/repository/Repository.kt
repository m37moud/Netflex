package com.example.netflex.repository

import com.example.netflex.model.MovieEntity
import com.example.netflex.retrofit.ApiResponse

interface Repository {

    suspend fun fetchTopRatedMovies(page: Int): ApiResponse?

    suspend fun fetchPopularMovies(page: Int): ApiResponse?

    suspend fun findMovieById(id: Int): MovieEntity?

    suspend fun insertMovieToDb(movieEntity: MovieEntity)

    suspend fun deleteMovie(movieEntity: MovieEntity)

    suspend fun checkIsSaved(id: Int): Boolean
}