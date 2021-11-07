package com.example.netflex

import com.example.netflex.model.MovieCategories

interface BuildFlavorConfig {
    val currentCategory: MovieCategories
        get() = MovieCategories.TopRated
}