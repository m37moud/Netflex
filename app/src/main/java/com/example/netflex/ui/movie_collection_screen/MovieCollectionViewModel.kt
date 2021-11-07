package com.example.netflex.ui.movie_collection_screen

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.netflex.model.Movie
import com.example.netflex.model.MovieCategories
import com.example.netflex.repository.MovieRepository
import com.example.netflex.retrofit.ApiResponse
import com.example.netflex.utils.ConnectionLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieCollectionViewModel(private val app: Application, private val movieRepository: MovieRepository) :
    ViewModel() {

    lateinit var connectionLiveData: ConnectionLiveData
    private var response: ApiResponse? = null
    var category: MovieCategories = MovieCategories.TopRated
        set(value) {
            _moviesLiveData.value!!.clear()
            response = null
            field = value
        }

    private val _moviesLiveData: MutableLiveData<ArrayList<Movie>> = MutableLiveData(arrayListOf())
    val moviesLiveData: LiveData<ArrayList<Movie>> get() = _moviesLiveData

    suspend fun addMoviesToRecyclerView() {
        if (connectionLiveData.value == false) return
        withContext(Dispatchers.IO) {
            response = getCorrespondingResponse()
        }
        _moviesLiveData.value!!.addAll(response?.results!!)
    }

    private suspend fun getCorrespondingResponse(): ApiResponse? {
        var mResponse: ApiResponse? = null

        when (category) {
            MovieCategories.Popular -> mResponse = fetchPopularMovies(response?.page?.plus(1) ?: 1)
            MovieCategories.TopRated -> mResponse = fetchTopRatedMovies(response?.page?.plus(1) ?: 1)
            MovieCategories.Favorite -> {
            } // TODO: Configure later
        }

        return mResponse
    }

    private suspend fun fetchPopularMovies(page: Int): ApiResponse? {
        return movieRepository.fetchPopularMovies(page)
    }

    private suspend fun fetchTopRatedMovies(page: Int): ApiResponse? {
        return movieRepository.fetchTopRatedMovies(page)
    }

    fun setupConnectionLivedata() {
        connectionLiveData = ConnectionLiveData(app.baseContext)
        val pingCommand = "ping -c 1 google.com"
        connectionLiveData.value = try {
            Runtime.getRuntime().exec(pingCommand).waitFor() == 0
        } catch (e: Exception) {
            false
        }
    }

}