package com.example.netflex.ui.movie_collection_screen

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.netflex.app.MyApp
import com.example.netflex.model.Movie
import com.example.netflex.model.MovieCategories
import com.example.netflex.retrofit.ApiResponse
import com.example.netflex.utils.ConnectionLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieCollectionViewModel(app: Application): ViewModel() {

    private val movieRepository = (app as MyApp).appComponent.getMovieRepository()
    private var response: ApiResponse? = null
    var category: MovieCategories = MovieCategories.TopRated
        set(value) {
            _moviesLiveData.value!!.clear()
            response = null
            field = value
        }

    val connectionLiveData = ConnectionLiveData(app.baseContext)

    private val _moviesLiveData: MutableLiveData<ArrayList<Movie>> = MutableLiveData(arrayListOf())
    val moviesLiveData: LiveData<ArrayList<Movie>> get() = _moviesLiveData

    suspend fun addMoviesToRecyclerView() {
        if (connectionLiveData.value == false || connectionLiveData.value == null) return
        withContext(Dispatchers.IO){
            response = getCorrespondingResponse()
        }
        _moviesLiveData.value!!.addAll(response?.results!!)
    }

    private suspend fun getCorrespondingResponse(): ApiResponse? {
        var mResponse: ApiResponse? = null

        when (category) {
            MovieCategories.Popular -> mResponse = fetchPopularMovies(response?.page?.plus(1) ?: 1)
            MovieCategories.TopRated -> mResponse = fetchTopRatedMovies(response?.page?.plus(1) ?: 1)
            MovieCategories.Favorite -> { } // TODO: Configure later
        }

        return mResponse
    }

    private suspend fun fetchPopularMovies(page: Int): ApiResponse? {
        return movieRepository.fetchPopularMovies(page)
    }

    private suspend fun fetchTopRatedMovies(page: Int): ApiResponse? {
        return movieRepository.fetchTopRatedMovies(page)
    }

}