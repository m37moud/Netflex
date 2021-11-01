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


class MovieCollectionFragment :
    BaseFragment<FragmentMovieCollectionBinding, MovieCollectionViewModel>() {

    override val viewModelClass: Class<MovieCollectionViewModel>
        get() = MovieCollectionViewModel::class.java

    private lateinit var popupMenu: PopupMenu
    override lateinit var binding: FragmentMovieCollectionBinding
    override lateinit var viewModel: MovieCollectionViewModel

    override fun initView(binding: FragmentMovieCollectionBinding) {
        // onCreateView
        this.binding = binding
        configurePopupMenu()
        configureConnectivity()
    }

    override fun onBindViewModel(viewModel: MovieCollectionViewModel) {
        // onCreate
        this.viewModel = viewModel
        setObserver()
    }

    private fun configureConnectivity() {
        viewModel.connectionLiveData.observe(viewLifecycleOwner) {
            binding.connectionLostLabel.isVisible = !it
            initRecyclerView()
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
            initRecyclerView()
            return@setOnMenuItemClickListener false
        }
    }

    private fun setObserver() {
        viewModel.responseLiveData.observe(requireActivity()) {
            (binding.rvMovies.adapter as MovieRecyclerAdapter?)?.setData(viewModel.movies)
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

    private fun setRecyclerAdapter(): MovieRecyclerAdapter {
        val adapter = MovieRecyclerAdapter(::loadContentToViewModel, ::onMovieClick)
        binding.rvMovies.adapter = adapter
        return adapter
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

    override fun inflate(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?,
        attachToRoot: Boolean
    ) = FragmentMovieCollectionBinding.inflate(layoutInflater, viewGroup, attachToRoot)
}