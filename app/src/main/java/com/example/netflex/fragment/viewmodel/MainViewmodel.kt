package com.example.netflex.fragment.viewmodel
import androidx.lifecycle.ViewModel
import com.example.netflex.di.DaggerRetrofitComponent
import com.example.netflex.di.RetrofitComponent
import com.example.netflex.model.ApiResponse
import com.example.netflex.repository.MovieRepository

class MainViewmodel: ViewModel() // TODO: maybe changed with AndroidViewModel() according to db implementation
{
    private val movieRepository = MovieRepository()

    companion object {
        val daggerRetrofitComponent: RetrofitComponent = DaggerRetrofitComponent.builder().build()
    }

    suspend fun fetchPopularMovies(page: Int): ApiResponse? {
        return movieRepository.fetchPopularMovies(page)
    }

    suspend fun fetchTopRatedMovies(page: Int): ApiResponse? {
        return movieRepository.fetchTopRatedMovies(page)
    }
}