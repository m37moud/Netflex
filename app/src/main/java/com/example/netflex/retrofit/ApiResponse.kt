package com.example.netflex.retrofit

import com.example.netflex.model.Movie

data class ApiResponse(
    var page: Int,
    val results: MutableList<Movie>
)
