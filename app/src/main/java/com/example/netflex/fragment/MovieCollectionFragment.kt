package com.example.netflex.fragment

import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.netflex.R
import com.example.netflex.adapter.MovieRecyclerAdapter
import com.example.netflex.databinding.FragmentMovieCollectionBinding
import com.example.netflex.fragment.base.BaseFragment
import com.example.netflex.fragment.viewmodel.MovieCollectionViewModel
import com.example.netflex.model.MovieEntity
import com.example.netflex.retrofit.ApiResponse
import com.example.netflex.retrofit.Resource
import com.example.netflex.utils.MovieCategories
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MovieCollectionFragment : BaseFragment<FragmentMovieCollectionBinding, MovieCollectionViewModel>(
    FragmentMovieCollectionBinding::inflate,
    MovieCollectionViewModel::class.java
    ) {
    private lateinit var adapter: MovieRecyclerAdapter
    private lateinit var popupMenu: PopupMenu

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureConnectivity()
        configurePopupMenu()
    }

    private fun configureConnectivity() {
        viewmodel.connectionLiveData.observe(requireActivity()) {
            binding?.connectionLostLabel?.isVisible = !it
            if ((it && binding?.rvMovies?.adapter == null) || (!it && binding?.rvMovies?.adapter == null && viewmodel.movies.isNotEmpty())) {
                // if connection restored which was lost from the oppening of the app
                // if connection was lost while browsing we don't want to call init function
                // if we rotated screen while offline
                initUI()
                setObserver()
            }else{
                initUI()
            }
        }
    }

    private fun configurePopupMenu() {
        popupMenu = PopupMenu(requireContext(), binding?.ibFilter)
        popupMenu.inflate(R.menu.popup_menu_filter)
        binding?.ibFilter?.setOnClickListener {
            popupMenu.show()
        }

        popupMenu.setOnMenuItemClickListener {
            val category = viewmodel.category
            when (it.itemId) {
                R.id.item_popular -> {
                    if (category != MovieCategories.Popular) viewmodel.category =
                        MovieCategories.Popular
                }
                R.id.item_top_rated -> {
                    if (category != MovieCategories.TopRated) viewmodel.category =
                        MovieCategories.TopRated
                }
                R.id.item_favorites -> {
                    if (category != MovieCategories.Favorite) viewmodel.category =
                        MovieCategories.Favorite
                }
                else -> {}
            }
            initUI()
            return@setOnMenuItemClickListener false
        }
    }

    private fun setObserver() {
        viewmodel.responseLiveData.observe(viewLifecycleOwner) {
            if (it is Resource.Error) {
                Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT).show()
                return@observe
            }
            if (viewmodel.category != MovieCategories.Favorite) (binding?.rvMovies?.adapter as MovieRecyclerAdapter).setData(it.d, viewmodel.movies)
        }
    }

    private fun initUI() {
        if (viewmodel.responseLiveData.value is Resource.Error)
        {
            Toast.makeText(requireContext(), (viewmodel.responseLiveData.value as Resource.Error<ApiResponse>).msg, Toast.LENGTH_SHORT).show()
            return
        }
        if (viewmodel.category == MovieCategories.Favorite){
            configureFavoriteMovies()
            return
        }
        if (viewmodel.movies.size != 0) { // used to restore state after rotating screen or changing fragment
            adapter = MovieRecyclerAdapter(null, ::pagingCallback, ::onMovieClick)
            adapter.setData(viewmodel.responseLiveData.value?.d, viewmodel.movies)
            binding?.rvMovies?.adapter = adapter
            return
        }
        if (!viewmodel.connectionLiveData.value!!) return
        adapter = MovieRecyclerAdapter(null, ::pagingCallback, ::onMovieClick)
        binding?.rvMovies?.adapter = adapter

        lifecycleScope.launch(Dispatchers.Main) {
            binding?.progressImages?.isVisible = true
            viewmodel.addMoviesToRecyclerView()
            binding?.progressImages?.isVisible = false
        }
    }

    private fun pagingCallback(page: Int) {
        if (!viewmodel.connectionLiveData.value!!) return
        lifecycleScope.launch(Dispatchers.Main) {
            binding?.progressImages?.isVisible = true
            viewmodel.addMoviesToRecyclerView(page)
            binding?.progressImages?.isVisible = false
        }
    }

    private fun onMovieClick(movie: MovieEntity){
        val action = MovieCollectionFragmentDirections.actionMovieCollectionFragmentToMovieDetailsFragment(movie)
        findNavController().navigate(action)
    }

    private fun configureFavoriteMovies(){
        lifecycleScope.launch {
            adapter = MovieRecyclerAdapter(null, null, ::onMovieClick)
            viewmodel.addMoviesToRecyclerView()
            adapter.setData(null, viewmodel.movies)
            binding?.rvMovies?.adapter = adapter
        }
    }
}