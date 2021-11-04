package com.example.netflex.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.netflex.R
import com.example.netflex.databinding.FragmentMovieDetailsBinding
import com.example.netflex.fragment.base.BaseFragment
import com.example.netflex.fragment.viewmodel.MovieDetailsViewModel
import com.example.netflex.model.MovieEntity
import com.example.netflex.utils.loadImage
import com.example.netflex.utils.setLifecycleObserver
import kotlinx.coroutines.launch

class MovieDetailsFragment :
    BaseFragment<FragmentMovieDetailsBinding, MovieDetailsViewModel>() {
    private val args: MovieDetailsFragmentArgs by navArgs()
    private val movie: MovieEntity
        get() = args.clickedMovie

    override lateinit var viewModel: MovieDetailsViewModel
    override val viewModelClass: Class<MovieDetailsViewModel>
        get() = MovieDetailsViewModel::class.java

    private lateinit var action: suspend (MovieEntity) -> Unit

    override fun onBindViewModel(viewmodel: MovieDetailsViewModel) {
        viewModel.movieEntity = movie
        configureAddToFavorites()
        initUI()
    }

    private fun initUI() {
        setLifecycleObserver(viewModel.movieLiveData){
            with(binding) {
                title.text = it.title
                originalTitle.text = it.original_title
                rating.text = it.vote_average.toString()
                description.text = it.overview
                releaseYear.text = it.release_date
                ivPoster.loadImage(requireContext(), it.generateImageUrl())
            }
        }

        setLifecycleObserver(viewModel.isFavorite){
            if (it){
                binding.btnFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
                action = viewModel::deleteFromFavorites
            }else{
                binding.btnFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                action = viewModel::addToFavorites
            }
        }
    }

    private fun configureAddToFavorites() {
        binding.btnFavorite.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                it.isClickable = false
                action(movie)
                it.isClickable = true
            }
        }
    }

    override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMovieDetailsBinding
        get() = FragmentMovieDetailsBinding::inflate
}