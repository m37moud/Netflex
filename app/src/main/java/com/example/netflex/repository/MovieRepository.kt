package com.example.netflex.repository

import com.example.netflex.di.component.DaggerAppComponent
import com.example.netflex.retrofit.ApiResponse

class MovieRepository {
    private val movieApi = DaggerAppComponent.builder().build().getMovieApi()

    suspend fun fetchPopularMovies(page: Int): ApiResponse? {
        return movieApi.getPopularMovies(page)
    }

    suspend fun fetchTopRatedMovies(page: Int): ApiResponse? {
        return movieApi.getTopRatedMovies(page)
    }

}