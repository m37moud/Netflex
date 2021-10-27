package com.example.netflex.fragment.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.netflex.app.MyApp
import com.example.netflex.model.MovieEntity
import com.example.netflex.retrofit.ApiResponse
import com.example.netflex.retrofit.Resource
import com.example.netflex.utils.ConnectionLiveData
import com.example.netflex.utils.MovieCategories
import java.lang.Exception

class MovieCollectionViewModel(app: Application): ViewModel() {

    var category: MovieCategories = MovieCategories.TopRated
        set(value) {
            movies.clear()
            field = value
        }

    val connectionLiveData = ConnectionLiveData(app.baseContext)

    private val movieRepository = (app as MyApp).appComponent.getMovieRepository()

    private val _responseLiveData = MutableLiveData<Resource<ApiResponse>>()
    val responseLiveData: LiveData<Resource<ApiResponse>> get() = _responseLiveData
    var movies = mutableListOf<MovieEntity>()

    suspend fun addMoviesToRecyclerView(page: Int = 1) {
        when (category) {
            MovieCategories.Popular -> {
                val res = getResult(::fetchPopularMovies, page)
                if (res is Resource.Success){
                    movies.addAll(res.data?.results!!)
                }
                _responseLiveData.value = res
            }
            MovieCategories.TopRated -> {
                val res = getResult(::fetchTopRatedMovies, page)
                if (res is Resource.Success){
                    movies.addAll(res.data?.results!!)
                }
                _responseLiveData.value = res
            }
            MovieCategories.Favorite -> {
                movies = movieRepository.fetchAllMovies()
            }
        }
    }

    private suspend fun getResult(gerRes: suspend (Int) -> ApiResponse?, page: Int): Resource<ApiResponse>{
        return try {
            val response = gerRes(page)
            Resource.Success(response)
        }catch (e: Exception){
            Resource.Error(msg=e.message)
        }
    }

    private suspend fun fetchPopularMovies(page: Int): ApiResponse? {
        return movieRepository.fetchPopularMovies(page)
    }

    private suspend fun fetchTopRatedMovies(page: Int): ApiResponse? {
        return movieRepository.fetchTopRatedMovies(page)
    }

}