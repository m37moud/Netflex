package com.example.netflex.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.netflex.R
import com.example.netflex.adapter.MovieRecyclerAdapter
import com.example.netflex.databinding.FragmentMovieCollectionBinding
import com.example.netflex.fragment.base.BaseFragment
import com.example.netflex.fragment.viewmodel.MovieCollectionViewModel
import com.example.netflex.model.MovieEntity
import com.example.netflex.utils.MovieCategories
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MovieCollectionFragment : BaseFragment<FragmentMovieCollectionBinding, MovieCollectionViewModel>() {

    override val viewModelClass: Class<MovieCollectionViewModel>
        get() = MovieCollectionViewModel::class.java

    private lateinit var adapter: MovieRecyclerAdapter
    private lateinit var popupMenu: PopupMenu
    override lateinit var binding: FragmentMovieCollectionBinding
    override lateinit var viewModel: MovieCollectionViewModel


    override fun initView(binding: FragmentMovieCollectionBinding) {
        // onCreateView
        this.binding = binding
        configureConnectivity()
        configurePopupMenu()
    }

    override fun onBindViewModel(viewModel: MovieCollectionViewModel) {
        // onCreate
        this.viewModel = viewModel
    }

    private fun configureConnectivity() {
        viewModel.connectionLiveData.observe(requireActivity()) {
            binding.connectionLostLabel.isVisible = !it
            if (binding.rvMovies.adapter == null) initRecyclerView()
            if (!viewModel.observed) setObserver()
        }
    }

    private fun configurePopupMenu() {
        popupMenu = PopupMenu(requireContext(), binding.ibFilter)
        popupMenu.inflate(R.menu.popup_menu_filter)
        binding.ibFilter.setOnClickListener {
            popupMenu.show()
        }

        popupMenu.setOnMenuItemClickListener {
            if (!viewModel.connectionLiveData.value!!) return@setOnMenuItemClickListener false
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
            initRecyclerView()
            return@setOnMenuItemClickListener false
        }
    }

    private fun setObserver() {
        viewModel.observed = true
        viewModel.responseLiveData.observe(requireActivity()) {
            (binding.rvMovies.adapter as MovieRecyclerAdapter).setData(it, viewModel.movies)
        }
    }

    private fun initRecyclerView() {
        if (viewModel.movies.size != 0) { // used to restore state after rotating screen or changing fragment
            adapter = MovieRecyclerAdapter(null, ::pagingCallback, ::onMovieClick)
            adapter.setData(viewModel.responseLiveData.value, viewModel.movies)
            binding.rvMovies.adapter = adapter
            return
        }
        if (!viewModel.connectionLiveData.value!!) return
        adapter = MovieRecyclerAdapter(null, ::pagingCallback, ::onMovieClick)
        binding.rvMovies.adapter = adapter

        lifecycleScope.launch(Dispatchers.Main) {
            binding.progressImages.isVisible = true
            viewModel.addMoviesToRecyclerView()
            binding.progressImages.isVisible = false
        }
    }

    private fun pagingCallback(page: Int) {
        if (!viewModel.connectionLiveData.value!!) return
        lifecycleScope.launch(Dispatchers.Main) {
            binding.progressImages.isVisible = true
            viewModel.addMoviesToRecyclerView(page)
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

    override fun inflate(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?,
        attachToRoot: Boolean
    ): FragmentMovieCollectionBinding =
        FragmentMovieCollectionBinding.inflate(layoutInflater, viewGroup, attachToRoot)
}