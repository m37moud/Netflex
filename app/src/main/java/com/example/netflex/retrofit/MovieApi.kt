package com.example.netflex.retrofit

import com.example.netflex.utils.RetrofitConstants
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET(RetrofitConstants.POPULAR_MOVIES_PATH)
    suspend fun getPopularMovies(
        @Query("page") page: Int
    ): ApiResponse?

    @GET(RetrofitConstants.TOP_RATED_MOVIES_PATH)
    suspend fun getTopRatedMovies(
        @Query("page") page: Int
    ): ApiResponse?

}