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
    val movies = mutableListOf<MovieEntity>()

    suspend fun addMoviesToRecyclerView() {
        if (connectionLiveData.value == false) return
        var response: ApiResponse? = null
        val page = _responseLiveData.value?.page?:0

        when (category) {
            MovieCategories.Popular -> {
                response = fetchPopularMovies(page + 1)
            }
            MovieCategories.TopRated -> {
                response = fetchTopRatedMovies(page + 1)
            }
            MovieCategories.Favorite -> {
            } // TODO: Configure later
        }

        _responseLiveData.value = response
        movies.addAll(response?.results!!)
    }

    private suspend fun fetchPopularMovies(page: Int): ApiResponse? {
        return movieRepository.fetchPopularMovies(page)
    }

    private suspend fun fetchTopRatedMovies(page: Int): ApiResponse? {
        return movieRepository.fetchTopRatedMovies(page)
    }

}