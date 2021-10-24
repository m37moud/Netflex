package com.example.netflex.retrofit

import com.example.netflex.model.MovieEntity

data class ApiResponse(
    var page: Int?,
    val results: MutableList<MovieEntity>?
)
