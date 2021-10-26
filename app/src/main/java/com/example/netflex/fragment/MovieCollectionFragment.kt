package com.example.netflex.fragment

import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.netflex.R
import com.example.netflex.adapter.MovieRecyclerAdapter
import com.example.netflex.databinding.FragmentMovieCollectionBinding
import com.example.netflex.fragment.base.BaseFragment
import com.example.netflex.fragment.viewmodel.MovieCollectionViewModel
import com.example.netflex.utils.ConnectionLiveData
import com.example.netflex.utils.MovieCategories
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MovieCollectionFragment : BaseFragment<FragmentMovieCollectionBinding, MovieCollectionViewModel>(
    FragmentMovieCollectionBinding::inflate,
    MovieCollectionViewModel::class.java
    ) {
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
            if ((it && binding?.rvMovies?.adapter == null) || (!it && binding?.rvMovies?.adapter == null && viewmodel.movies.isNotEmpty())) {
                // if connection restored which was lost from the oppening of the app
                // if connection was lost while browsing we don't want to call init function
                // if we rotated screen while offline
                initUI()
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
            (binding?.rvMovies?.adapter as MovieRecyclerAdapter).setData(it, viewmodel.movies)
        }
    }

    private fun initUI() {
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
            viewmodel.addMoviesToRecyclerView()
            binding?.progressImages?.isVisible = false
        }
    }

    private fun pagingCallback(page: Int) {
        if (!connectionLiveData.value!!) return
        lifecycleScope.launch(Dispatchers.Main) {
            binding?.progressImages?.isVisible = true
            viewmodel.addMoviesToRecyclerView(page)
            binding?.progressImages?.isVisible = false
        }
    }
}