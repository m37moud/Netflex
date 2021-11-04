package com.example.netflex.ui.movie_details_screen

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.netflex.app.MyApp
import com.example.netflex.model.Movie
import com.example.netflex.repository.MovieRepository
import com.example.netflex.utils.getImageAsBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailsViewModel(val app: Application): ViewModel() {
    private val movieRepository: MovieRepository = (app as MyApp).appComponent.getMovieRepository()
    var movie: Movie? = null
    set(value) {
        _movieLiveData.value = value
        initIsFavorite(value?.id?:0)
        field = value
    }

    private val _movieLiveData: MutableLiveData<Movie> = MutableLiveData()
    val movieLiveData: LiveData<Movie> get() = _movieLiveData

    private val _isFavorite: MutableLiveData<Boolean> = MutableLiveData()
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    private fun initIsFavorite(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            _isFavorite.postValue(movieRepository.checkIsSaved(id))
        }
    }

    suspend fun addToFavorites(movie: Movie){
        withContext(Dispatchers.IO){
            try {
                movie.poster = movie.generateImageUrl().getImageAsBitmap(app)
                movieRepository.insertMovieToDb(movie)
                _isFavorite.postValue(true)
            }
            catch (e: Exception) { Toast.makeText(app, "Couldn't Save Movie Try Again", Toast.LENGTH_SHORT).show() }
        }
    }

    suspend fun deleteFromFavorites(movie: Movie){
        movieRepository.deleteMovie(movie)
        _isFavorite.postValue(false)
    }

}