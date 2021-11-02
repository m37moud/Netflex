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
import com.example.netflex.utils.setLifecycleObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MovieCollectionFragment :
    BaseFragment<FragmentMovieCollectionBinding, MovieCollectionViewModel>() {

    override val viewModelClass: Class<MovieCollectionViewModel>
        get() = MovieCollectionViewModel::class.java

    private lateinit var popupMenu: PopupMenu
    override lateinit var viewModel: MovieCollectionViewModel

    override fun onBindViewModel(viewModel: MovieCollectionViewModel) {
        this.viewModel = viewModel
        configurePopupMenu()
        initRecyclerView()
        setObserver()
        configureConnectivity()
    }

    private fun configureConnectivity() {
        setLifecycleObserver(viewModel.connectionLiveData) {
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
                R.id.item_popular -> if (category != MovieCategories.Popular) viewModel.category = MovieCategories.Popular
                R.id.item_top_rated -> if (category != MovieCategories.TopRated) viewModel.category = MovieCategories.TopRated
                R.id.item_favorites -> if (category != MovieCategories.Favorite) viewModel.category = MovieCategories.Favorite
                else -> {}
            }
            setRecyclerData()
            return@setOnMenuItemClickListener false
        }
    }

    private fun setObserver() {
       setLifecycleObserver(viewModel.responseLiveData) {
            setRecyclerData()
        }
    }

    private fun initRecyclerView() {
        if (viewModel.movies.size != 0) setRecyclerAdapter().setData(viewModel.movies)// used to restore state after rotating screen or changing fragment
        else{
            setRecyclerAdapter()
            loadContentToViewModel()
        }
    }

    private fun setRecyclerData() {
        if (viewModel.movies.isEmpty()) loadContentToViewModel()
        (binding.rvMovies.adapter as MovieRecyclerAdapter?)?.setData(viewModel.movies)
    }

    private fun setRecyclerAdapter(): MovieRecyclerAdapter {
        val adapter = MovieRecyclerAdapter(::onMovieClick)
        val spancount = if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 4
        val manager = GridLayoutManager(requireContext(), spancount)
        with(binding){
            rvMovies.adapter = adapter
            rvMovies.layoutManager = manager
            rvMovies.setOnScrollChangeListener(RecyclerScrollListener(manager, ::loadContentToViewModel))
        }
        return adapter
    }

    private fun loadContentToViewModel(){
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
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

    override val inflate: (layoutInflater: LayoutInflater, viewGroup: ViewGroup?, attachToRoot: Boolean) -> FragmentMovieCollectionBinding
        get() = FragmentMovieCollectionBinding::inflate
}