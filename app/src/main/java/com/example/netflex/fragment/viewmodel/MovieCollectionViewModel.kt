package com.example.netflex.fragment.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.netflex.app.MyApp
import com.example.netflex.model.MovieEntity
import com.example.netflex.retrofit.ApiResponse
import com.example.netflex.utils.ConnectionLiveData
import com.example.netflex.utils.MovieCategories

class MovieCollectionViewModel(app: Application): ViewModel() {

    var category: MovieCategories = MovieCategories.TopRated
        set(value) {
            movies.clear()
            field = value
        }

    val connectionLiveData = ConnectionLiveData(app.baseContext)

    private val movieRepository = (app as MyApp).appComponent.getMovieRepository()

    private val _responseLiveData = MutableLiveData<ApiResponse>()
    val responseLiveData: LiveData<ApiResponse> get() = _responseLiveData
    var movies = mutableListOf<MovieEntity>()

    suspend fun addMoviesToRecyclerView(page: Int = 1) {
        val response: ApiResponse?

        when (category) {
            MovieCategories.Popular -> {
                response = fetchPopularMovies(page)
                movies.addAll(response?.results!!)
                _responseLiveData.value = response
            }
            MovieCategories.TopRated -> {
                response = fetchTopRatedMovies(page)
                movies.addAll(response?.results!!)
                _responseLiveData.value = response
            }
            MovieCategories.Favorite -> {
                movies = movieRepository.fetchAllMovies()
            }
        }
    }

    private suspend fun fetchPopularMovies(page: Int): ApiResponse? {
        return movieRepository.fetchPopularMovies(page)
    }

    private suspend fun fetchTopRatedMovies(page: Int): ApiResponse? {
        return movieRepository.fetchTopRatedMovies(page)
    }

}