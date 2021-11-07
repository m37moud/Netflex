package com.example.netflex.ui.movie_collection_screen

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.netflex.BuildFlavorConfig
import com.example.netflex.app.MyApp
import com.example.netflex.model.Movie
import com.example.netflex.model.MovieCategories
import com.example.netflex.retrofit.ApiResponse
import com.example.netflex.utils.ConnectionLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieCollectionViewModel(app: Application): ViewModel(), BuildFlavorConfig {

    private val movieRepository = (app as MyApp).appComponent.getMovieRepository()
    private var response: ApiResponse? = null
    var category: MovieCategories = currentCategory
        set(value) {
            movies.clear()
            response = null
            field = value
        }

    val connectionLiveData = ConnectionLiveData(app.baseContext)
    private val movies: MutableList<Movie> = mutableListOf()

    private val _moviesLiveData: MutableLiveData<List<Movie>> = MutableLiveData(movies)
    val moviesLiveData: LiveData<List<Movie>> get() = _moviesLiveData

    suspend fun addMoviesToRecyclerView() {
        if ((connectionLiveData.value == false || connectionLiveData.value == null) && category != MovieCategories.Favorite) return
        withContext(Dispatchers.IO){
            response = getCorrespondingResponse()
        }
    }

    private suspend fun getCorrespondingResponse(): ApiResponse {
        val mResponse: ApiResponse? = when (category) {
            MovieCategories.Popular -> fetchPopularMovies(response?.page?.plus(1) ?: 1)
            MovieCategories.TopRated -> fetchTopRatedMovies(response?.page?.plus(1) ?: 1)
            MovieCategories.Favorite -> ApiResponse(-1, movieRepository.fetchAllSavedMovies())
        }
        movies.addAll(mResponse?.results!!)
        _moviesLiveData.postValue(movies)
        return mResponse
    }

    private suspend fun fetchPopularMovies(page: Int): ApiResponse? {
        return movieRepository.fetchPopularMovies(page)
    }

    private suspend fun fetchTopRatedMovies(page: Int): ApiResponse? {
        return movieRepository.fetchTopRatedMovies(page)
    }

}