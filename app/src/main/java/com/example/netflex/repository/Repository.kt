package com.example.netflex.repository

import com.example.netflex.retrofit.ApiResponse

interface Repository {

    suspend fun fetchTopRatedMovies(page: Int): ApiResponse?

    suspend fun fetchPopularMovies(page: Int): ApiResponse?
}