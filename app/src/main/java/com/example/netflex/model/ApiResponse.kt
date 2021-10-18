package com.example.netflex.model

data class ApiResponse(
    var page: Int?,
    val results: MutableList<Movie>?
){ // TODO: add category codes with companion object to differentiate results

    data class Movie(
        val title: String,
        val poster_path: String,
        val overview: String,
        val original_title: String,
        val vote_average: Double,
        val release_date: String
    )
}
