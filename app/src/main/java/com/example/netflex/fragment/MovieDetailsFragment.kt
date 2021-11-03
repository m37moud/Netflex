package com.example.netflex.fragment

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
import com.example.netflex.utils.addPrefix
import com.example.netflex.utils.getImageAsBitmap
import com.example.netflex.utils.loadImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailsFragment :
    BaseFragment<FragmentMovieDetailsBinding, MovieDetailsViewModel>() {
    private val args: MovieDetailsFragmentArgs by navArgs()
    private val movie: MovieEntity = args.clickedMovie

    override lateinit var viewModel: MovieDetailsViewModel
    override val viewModelClass: Class<MovieDetailsViewModel>
        get() = MovieDetailsViewModel::class.java

    override fun onBindViewModel() {
        lifecycleScope.launch {
            viewModel.isFavorite = viewModel.isFavorite(movie.id)
            initUI()
        }
        configureAddToFavorites()
    }

    private fun initUI() {
        with(binding) {
            title.text = movie.title
            originalTitle.text = movie.original_title.addPrefix("Original title: ")
            rating.text = movie.vote_average.toString().addPrefix("Rating: ")
            description.text = movie.overview.addPrefix("Overview:\n")
            releaseYear.text = movie.release_date.addPrefix("Release date: ")
            ivPoster.loadImage(requireContext(), movie.generateImageUrl())
            if (viewModel.isFavorite) this.btnFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
        }
    }

    private fun configureAddToFavorites() {
        binding.btnFavorite.setOnClickListener {

            lifecycleScope.launch {
                it.isClickable = false
                if (viewModel.isFavorite) {
                    viewModel.deleteFromFavorites(movie)
                    (it as ImageButton).setImageResource(R.drawable.ic_baseline_favorite_border_24)
                    viewModel.isFavorite = !viewModel.isFavorite
                } else {
                    try {
                        withContext(Dispatchers.IO) {
                            movie.poster =
                                movie.generateImageUrl().getImageAsBitmap(requireContext())
                        }

                        viewModel.addToFavorites(movie)
                        (it as ImageButton).setImageResource(R.drawable.ic_baseline_favorite_24)
                        viewModel.isFavorite = !viewModel.isFavorite

                    } catch (e: Exception) {
                        Toast.makeText(
                            requireContext(),
                            "Couldn't Save Movie Try Again",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                it.isClickable = true
            }

        }
    }

    override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMovieDetailsBinding
        get() = FragmentMovieDetailsBinding::inflate
}