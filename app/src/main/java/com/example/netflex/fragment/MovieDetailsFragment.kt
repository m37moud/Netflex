package com.example.netflex.fragment

import android.util.Log.d
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
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

    override fun onBindViewModel(viewmodel: MovieDetailsViewModel) {
        viewModel.movieEntity = movie
        configureAddToFavorites()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isFavorite = viewModel.isFavorite(movie.id)
            initUI()
        }
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
                if (viewModel.isFavorite) btnFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
            }
        }
    }

    private fun configureAddToFavorites() {
        binding.btnFavorite.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                it.isClickable = false
                if (viewModel.isFavorite) {
                    viewModel.deleteFromFavorites(movie)
                    (it as ImageButton).setImageResource(R.drawable.ic_baseline_favorite_border_24)
                    viewModel.isFavorite = !viewModel.isFavorite
                } else {
                    try {
                        viewModel.addToFavorites(movie)
                        (it as ImageButton).setImageResource(R.drawable.ic_baseline_favorite_24)
                        viewModel.isFavorite = !viewModel.isFavorite
                    } catch (e: Exception) { Toast.makeText(requireContext(), "Couldn't Save Movie Try Again", Toast.LENGTH_SHORT).show() }
                }
                it.isClickable = true
            }
        }
    }

    override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMovieDetailsBinding
        get() = FragmentMovieDetailsBinding::inflate
}