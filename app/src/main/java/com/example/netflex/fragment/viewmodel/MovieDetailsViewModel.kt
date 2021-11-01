package com.example.netflex.fragment.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.netflex.app.MyApp
import com.example.netflex.fragment.MovieDetailsFragmentArgs
import com.example.netflex.model.MovieEntity
import com.example.netflex.repository.MovieRepository

class MovieDetailsViewModel(app: Application): ViewModel() {
    private val movieRepository: MovieRepository = (app as MyApp).appComponent.getMovieRepository()

    var isFavorite = false

    suspend fun isFavorite(id: Int): Boolean{
        return movieRepository.findMovieById(id) != null
    }

    suspend fun addToFavorites(movieEntity: MovieEntity){
        movieRepository.insertMovieToDb(movieEntity)
    }

    suspend fun deleteFromFavorites(movieEntity: MovieEntity){
        movieRepository.deleteMovie(movieEntity)
    }

}