package com.example.netflex.fragment

import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.netflex.R
import com.example.netflex.adapter.MovieRecyclerAdapter
import com.example.netflex.databinding.FragmentMovieCollectionBinding
import com.example.netflex.fragment.base.BaseFragment
import com.example.netflex.model.ApiResponse
import com.example.netflex.utils.ConnectionLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MovieCollectionFragment : BaseFragment<FragmentMovieCollectionBinding>(
    FragmentMovieCollectionBinding::inflate) {
    private lateinit var adapter: MovieRecyclerAdapter
    private lateinit var connectionLiveData: ConnectionLiveData
    private lateinit var popupMenu: PopupMenu

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        connectionLiveData = ConnectionLiveData(requireContext())
        configureConnectivity()
        configurePopupMenu()
    }

    private fun configureConnectivity() {
        connectionLiveData.observe(viewLifecycleOwner) {
            binding?.connectionLostLabel?.isVisible = !it
            if (it && binding?.rvMovies?.adapter == null) {
                // if connection restored which was lost from the oppening of the app
                // if connection was lost while browsing we don't want to call init function
                init()
                setObserver()
            }else if (!it && binding?.rvMovies?.adapter == null && viewmodel.movies.isNotEmpty()){
                // if we rotated screen while offline
                init()
                setObserver()
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
            if (!connectionLiveData.value!!) return@setOnMenuItemClickListener false
            val category = viewmodel.category
            when (it.itemId) {
                R.id.item_popular -> {
                    if (category != ApiResponse.Movie.CATEGORY_POPULAR) viewmodel.category =
                        ApiResponse.Movie.CATEGORY_POPULAR
                }
                R.id.item_top_rated -> {
                    if (category != ApiResponse.Movie.CATEGORY_TOP_RATED) viewmodel.category =
                        ApiResponse.Movie.CATEGORY_TOP_RATED
                }
                R.id.item_favorites -> {
                    if (category != ApiResponse.Movie.CATEGORY_FAVORITES) viewmodel.category =
                        ApiResponse.Movie.CATEGORY_FAVORITES
                }
                else -> {}
            }
            init()
            return@setOnMenuItemClickListener false
        }
    }

    private fun setObserver() {
        viewmodel.responseLiveData.observe(viewLifecycleOwner) {
            (binding?.rvMovies?.adapter as MovieRecyclerAdapter).setData(it, viewmodel.movies)
        }
    }

    private fun init() {
        if (viewmodel.movies.size != 0) { // used to restore state after rotating screen or changing fragment
            adapter = MovieRecyclerAdapter(null, ::pagingCallback)
            adapter.setData(viewmodel.responseLiveData.value, viewmodel.movies)
            binding?.rvMovies?.adapter = adapter
            return
        }
        if (!connectionLiveData.value!!) return
        adapter = MovieRecyclerAdapter(null, ::pagingCallback)
        binding?.rvMovies?.adapter = adapter

        lifecycleScope.launch(Dispatchers.Main) {
            binding?.progressImages?.isVisible = true
            viewmodel.populateMovieRecyclerView()
            binding?.progressImages?.isVisible = false
        }
    }

    private fun pagingCallback(page: Int) {
        if (!connectionLiveData.value!!) return
        lifecycleScope.launch(Dispatchers.Main) {
            binding?.progressImages?.isVisible = true
            viewmodel.populateMovieRecyclerView(page)
            binding?.progressImages?.isVisible = false
        }
    }
}