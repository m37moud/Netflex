package com.example.netflex.fragment.viewmodel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.netflex.di.DaggerRetrofitComponent
import com.example.netflex.di.RetrofitComponent
import com.example.netflex.model.ApiResponse
import com.example.netflex.repository.MovieRepository

class MoviesViewmodel: ViewModel() // TODO: maybe changed with AndroidViewModel() according to db implementation
{
    companion object {
        val daggerRetrofitComponent: RetrofitComponent = DaggerRetrofitComponent.builder().build()
    }

    var category = ApiResponse.Movie.CATEGORY_TOP_RATED
        set(value) {
            movies.clear()
            field = value
        }

    private val movieRepository = MovieRepository()
    private val _responseLiveData = MutableLiveData<ApiResponse>()
    val responseLiveData: LiveData<ApiResponse> get() = _responseLiveData
    val movies = mutableListOf<ApiResponse.Movie>()

    suspend fun addMoviesToRecyclerView(page: Int = 1){
        var response: ApiResponse? = null

        when(category){
            ApiResponse.Movie.CATEGORY_POPULAR -> {
                response = fetchPopularMovies(page)
            }
            ApiResponse.Movie.CATEGORY_TOP_RATED -> {
                response = fetchTopRatedMovies(page)
            }
            ApiResponse.Movie.CATEGORY_FAVORITES -> {} // TODO: Configure later
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