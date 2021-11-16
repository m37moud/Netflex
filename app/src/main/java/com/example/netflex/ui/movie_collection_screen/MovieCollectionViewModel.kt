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

class MovieCollectionViewModel(private val app: Application,
                               private val movieRepository: MovieRepository) : ViewModel() {

    lateinit var connectionLiveData: ConnectionLiveData
    private var response: ApiResponse? = null
    private val movies: ArrayList<Movie> = arrayListOf()
    var category: MovieCategories = MovieCategories.TopRated
        set(value) {
            movies.clear()
            response = null
            field = value
        }

    private val _moviesLiveData: MutableLiveData<ArrayList<Movie>> = MutableLiveData(movies)
    val moviesLiveData: LiveData<ArrayList<Movie>> get() = _moviesLiveData

    suspend fun addMoviesToRecyclerView() {
        if (connectionLiveData.value == false && category != MovieCategories.Favorite) return
        withContext(Dispatchers.IO) {
            response = getCorrespondingResponse()
        }
        movies.addAll(response?.results!!)
        _moviesLiveData.value = movies
    }

    private suspend fun getCorrespondingResponse(): ApiResponse? {
        return when (category) {
            MovieCategories.Popular -> fetchPopularMovies(response?.page?.plus(1) ?: 1)
            MovieCategories.TopRated -> fetchTopRatedMovies(response?.page?.plus(1) ?: 1)
            MovieCategories.Favorite -> {
                movies.clear()
                ApiResponse(-1, fetchAllSavedMovies())
            }
        }
    }

    private suspend fun fetchPopularMovies(page: Int) = movieRepository.fetchPopularMovies(page)

    private suspend fun fetchTopRatedMovies(page: Int) = movieRepository.fetchTopRatedMovies(page)

    private suspend fun fetchAllSavedMovies() = movieRepository.fetchAllSavedMovies()

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