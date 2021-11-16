package com.example.netflex.model

sealed class MovieCategories{
    object Popular: MovieCategories()
    object TopRated: MovieCategories()
    object Favorite: MovieCategories()
}
