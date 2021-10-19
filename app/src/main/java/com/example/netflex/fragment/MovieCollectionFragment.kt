package com.example.netflex.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.netflex.R
import com.example.netflex.adapter.MovieRecyclerAdapter
import com.example.netflex.databinding.FragmentMovieCollectionBinding
import com.example.netflex.fragment.base.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MovieCollectionFragment : BaseFragment<FragmentMovieCollectionBinding>(
    FragmentMovieCollectionBinding::inflate) {
    private lateinit var adapter: MovieRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

    }

    private fun init(){ // TODO: Configure passing data from viewmodel to adapter using LiveData
        lifecycleScope.launch(Dispatchers.Main) {
            binding?.progressImages?.isVisible = true
            val data = viewmodel.fetchPopularMovies(1)
            adapter = MovieRecyclerAdapter(data, ::pagingCallback)
            binding?.rvMovies?.adapter = adapter
            binding?.progressImages?.isVisible = false
        }
    }

    private fun pagingCallback(page: Int){ // TODO: Configure from viewmodel
        lifecycleScope.launch(Dispatchers.Main) {
            binding?.progressImages?.isVisible = true
            val data = viewmodel.fetchPopularMovies(page + 1)
            (binding?.rvMovies?.adapter as MovieRecyclerAdapter).addData(data)
            binding?.progressImages?.isVisible = false
        }
    }
}