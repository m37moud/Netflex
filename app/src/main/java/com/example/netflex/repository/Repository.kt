package com.example.netflex.repository

import com.example.netflex.model.Movie
import com.example.netflex.retrofit.ApiResponse

interface Repository {

    suspend fun fetchTopRatedMovies(page: Int): ApiResponse?

    suspend fun fetchPopularMovies(page: Int): ApiResponse?

    suspend fun findMovieById(id: Int): Movie?

    suspend fun insertMovieToDb(movie: Movie)

    suspend fun deleteMovie(movie: Movie)

    suspend fun checkIsSaved(id: Int): Boolean

    suspend fun fetchAllSavedMovies(): List<Movie>
}