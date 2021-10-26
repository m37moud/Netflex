package com.example.netflex.utils

sealed class MovieCategories{
    object Popular: MovieCategories()
    object TopRated: MovieCategories()
    object Favorite: MovieCategories()
}
