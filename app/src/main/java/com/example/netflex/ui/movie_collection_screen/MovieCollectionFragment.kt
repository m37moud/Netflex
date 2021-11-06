package com.example.netflex.ui.movie_collection_screen

import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.netflex.R
import com.example.netflex.databinding.FragmentMovieCollectionBinding
import com.example.netflex.model.Movie
import com.example.netflex.model.MovieCategories
import com.example.netflex.ui.adapter.MovieRecyclerAdapter
import com.example.netflex.ui.adapter.scroll_listener.RecyclerScrollListener
import com.example.netflex.ui.base.BaseFragment
import com.example.netflex.utils.setLifecycleObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MovieCollectionFragment :
    BaseFragment<FragmentMovieCollectionBinding, MovieCollectionViewModel>() {

    override val viewModelClass: Class<MovieCollectionViewModel>
        get() = MovieCollectionViewModel::class.java

    private lateinit var popupMenu: PopupMenu
    override lateinit var viewModel: MovieCollectionViewModel
    private lateinit var adapter: MovieRecyclerAdapter

    override fun onBindViewModel(vm: MovieCollectionViewModel) {
        configurePopupMenu()
        configureConnectivity()
        adapter = setRecyclerAdapter()
        setLifecycleObserver(vm.moviesLiveData) {
            adapter.setData(it)
        }

    }

    private fun configureConnectivity() {
        setLifecycleObserver(viewModel.connectionLiveData) {
            binding.connectionLostLabel.isVisible = !it
            if (viewModel.moviesLiveData.value!!.isEmpty()) loadContentToViewModel()
        }
    }

    private fun configurePopupMenu() {
        popupMenu = PopupMenu(requireContext(), binding.ibFilter)
        popupMenu.inflate(R.menu.popup_menu_filter)
        binding.ibFilter.setOnClickListener {
            popupMenu.show()
        }
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_popular -> viewModel.category = MovieCategories.Popular
                R.id.item_top_rated -> viewModel.category = MovieCategories.TopRated
                R.id.item_favorites -> viewModel.category = MovieCategories.Favorite
            }
            loadContentToViewModel()
            false
        }
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

    private fun loadContentToViewModel(isPagingCallback: Boolean = false){
        if (isPagingCallback && viewModel.category == MovieCategories.Favorite) return
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            binding.progressImages.isVisible = true
            viewModel.addMoviesToRecyclerView()
            binding.progressImages.isVisible = false
        }
    }

    private fun onMovieClick(movie: Movie) {
        val action =
            MovieCollectionFragmentDirections.actionMovieCollectionFragmentToMovieDetailsFragment(
                movie
            )
        findNavController().navigate(action)
    }

    override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMovieCollectionBinding
        get() = FragmentMovieCollectionBinding::inflate
}