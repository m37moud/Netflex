package com.example.netflex.repository

import com.example.netflex.fragment.viewmodel.MainViewmodel
import com.example.netflex.model.ApiResponse

class MovieRepository {
    private var movieApi = MainViewmodel.daggerRetrofitComponent.getMovieApi()

    suspend fun fetchPopularMovies(page: Int): ApiResponse? {
        return movieApi.getPopularMovies(page)
    }

    suspend fun fetchTopRatedMovies(page: Int): ApiResponse? {
        return movieApi.getTopRatedMovies(page)
    }

}