package com.example.netflex.fragment

import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.netflex.R
import com.example.netflex.adapter.MovieRecyclerAdapter
import com.example.netflex.adapter.scroll_listener.RecyclerScrollListener
import com.example.netflex.databinding.FragmentMovieCollectionBinding
import com.example.netflex.fragment.base.BaseFragment
import com.example.netflex.fragment.viewmodel.MovieCollectionViewModel
import com.example.netflex.model.MovieEntity
import com.example.netflex.utils.MovieCategories
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MovieCollectionFragment :
    BaseFragment<FragmentMovieCollectionBinding, MovieCollectionViewModel>() {

    override val viewModelClass: Class<MovieCollectionViewModel>
        get() = MovieCollectionViewModel::class.java

    private lateinit var popupMenu: PopupMenu
    override lateinit var viewModel: MovieCollectionViewModel

    override fun onBindViewModel(viewmodel: MovieCollectionViewModel) {
        configurePopupMenu()
        initRecyclerView()
        configureConnectivity()
        viewmodel.responseLiveData.observe(this) {
            setRecyclerData()
        }
    }

    private fun configureConnectivity() {
        viewModel.connectionLiveData.observe(this) {
            binding.connectionLostLabel.isVisible = !it
            setRecyclerData()
        }
    }

    private fun configurePopupMenu() {
        popupMenu = PopupMenu(requireContext(), binding.ibFilter)
        popupMenu.inflate(R.menu.popup_menu_filter)
        binding.ibFilter.setOnClickListener {
            popupMenu.show()
        }

        popupMenu.setOnMenuItemClickListener {
            val category = viewModel.category
            when (it.itemId) {
                R.id.item_popular -> {
                    if (category != MovieCategories.Popular) viewModel.category =
                        MovieCategories.Popular
                }
                R.id.item_top_rated -> {
                    if (category != MovieCategories.TopRated) viewModel.category =
                        MovieCategories.TopRated
                }
                R.id.item_favorites -> {
                    if (category != MovieCategories.Favorite) viewModel.category =
                        MovieCategories.Favorite
                }
                else -> {
                }
            }
            setRecyclerData()
            return@setOnMenuItemClickListener false
        }
    }

    private fun initRecyclerView() {
        if (viewModel.movies.size != 0) { // used to restore state after rotating screen or changing fragment
            setRecyclerAdapter().setData(viewModel.movies)
            return
        }
        setRecyclerAdapter()
        loadContentToViewModel()
    }

    private fun setRecyclerData() {
        if (viewModel.movies.isEmpty()) loadContentToViewModel()
        (binding.rvMovies.adapter as MovieRecyclerAdapter?)?.setData(viewModel.movies)
    }

    private fun setRecyclerAdapter(): MovieRecyclerAdapter {
        val mAdapter = MovieRecyclerAdapter(::onMovieClick)
        val spancount = if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 4
        val manager = GridLayoutManager(requireContext(), spancount)
        with(binding.rvMovies){
            adapter = mAdapter
            layoutManager = manager
            addOnScrollListener(RecyclerScrollListener(manager, ::loadContentToViewModel))
        }
        return mAdapter
    }

    private fun loadContentToViewModel(){
        lifecycleScope.launch(Dispatchers.Main) {
            binding.progressImages.isVisible = true
            viewModel.addMoviesToRecyclerView()
            binding.progressImages.isVisible = false
        }
    }

    private fun onMovieClick(movie: MovieEntity) {
        val action =
            MovieCollectionFragmentDirections.actionMovieCollectionFragmentToMovieDetailsFragment(
                movie
            )
        findNavController().navigate(action)
    }

    override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMovieCollectionBinding
        get() = FragmentMovieCollectionBinding::inflate
}