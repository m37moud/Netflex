package com.example.netflex.fragment.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.netflex.app.MyApp
import com.example.netflex.model.MovieEntity
import com.example.netflex.repository.MovieRepository
import com.example.netflex.utils.getImageAsBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailsViewModel(val app: Application): ViewModel() {
    private val movieRepository: MovieRepository = (app as MyApp).appComponent.getMovieRepository()
    var movieEntity: MovieEntity? = null
    set(value) {
        _movieLiveData.value = value
        initIsFavorite(value?.id?:0)
        field = value
    }

    private val _movieLiveData: MutableLiveData<MovieEntity> = MutableLiveData()
    val movieLiveData: LiveData<MovieEntity> get() = _movieLiveData

    private val _isFavorite: MutableLiveData<Boolean> = MutableLiveData()
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    private fun initIsFavorite(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            _isFavorite.postValue(movieRepository.checkIsSaved(id))
        }
    }

    suspend fun addToFavorites(movieEntity: MovieEntity){
        withContext(Dispatchers.IO){
            try {
                movieEntity.poster = movieEntity.generateImageUrl().getImageAsBitmap(app)
                movieRepository.insertMovieToDb(movieEntity)
                _isFavorite.postValue(true)
            }
            catch (e: Exception) { Toast.makeText(app, "Couldn't Save Movie Try Again", Toast.LENGTH_SHORT).show() }
        }
    }

    suspend fun deleteFromFavorites(movieEntity: MovieEntity){
        movieRepository.deleteMovie(movieEntity)
        _isFavorite.postValue(false)
    }

}