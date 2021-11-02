package com.example.netflex.fragment.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.netflex.app.MyApp
import com.example.netflex.model.MovieEntity
import com.example.netflex.repository.MovieRepository
import com.example.netflex.utils.getImageAsBitmap

class MovieDetailsViewModel(val app: Application): ViewModel() {
    private val movieRepository: MovieRepository = (app as MyApp).appComponent.getMovieRepository()
    var movieEntity: MovieEntity? = null
    set(value) {
        _movieLiveData.value = value
        field = value
    }

    private val _movieLiveData: MutableLiveData<MovieEntity> = MutableLiveData()
    val movieLiveData: LiveData<MovieEntity> get() = _movieLiveData


    var isFavorite = false

    suspend fun isFavorite(id: Int): Boolean{
        return movieRepository.findMovieById(id) != null
    }

    suspend fun addToFavorites(movieEntity: MovieEntity){
        movieEntity.poster = movieEntity.generateImageUrl().getImageAsBitmap(app.baseContext)
        movieRepository.insertMovieToDb(movieEntity)
    }

    suspend fun deleteFromFavorites(movieEntity: MovieEntity){
        movieRepository.deleteMovie(movieEntity)
    }

}