package com.example.netflex.fragment

import android.os.Bundle
import android.view.View
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
import java.lang.Exception

class MovieDetailsFragment :
    BaseFragment<FragmentMovieDetailsBinding, MovieDetailsViewModel>
        (
        FragmentMovieDetailsBinding::inflate,
        MovieDetailsViewModel::class.java
    ) {
    private val args: MovieDetailsFragmentArgs by navArgs()
    private lateinit var movie: MovieEntity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movie = args.clickedMovie
        lifecycleScope.launch {
            viewmodel.isFavorite = viewmodel.isFavorite(movie.id)
            initUI()
        }
        configureAddToFavorites()
    }

    private fun initUI() {
        if (binding == null) return
        with(binding) {
            this?.title?.text = movie.title
            this?.originalTitle?.text = movie.original_title.addPrefix("Original title: ")
            this?.rating?.text = movie.vote_average.toString().addPrefix("Rating: ")
            this?.description?.text = movie.overview.addPrefix("Overview:\n")
            this?.releaseYear?.text = movie.release_date.addPrefix("Release date: ")
            this?.ivPoster?.loadImage(requireContext(), movie.generateImageUrl())
            if (viewmodel.isFavorite) this?.btnFavorite?.setImageResource(R.drawable.ic_baseline_favorite_24)
        }
    }

    private fun configureAddToFavorites() {
        binding?.btnFavorite?.setOnClickListener {

            lifecycleScope.launch {
                it.isClickable = false
                if (viewmodel.isFavorite) {
                    viewmodel.deleteFromFavorites(movie)
                    (it as ImageButton).setImageResource(R.drawable.ic_baseline_favorite_border_24)
                    viewmodel.isFavorite = !viewmodel.isFavorite
                } else {
                    try {
                        withContext(Dispatchers.IO) {
                            movie.poster =
                                movie.generateImageUrl().getImageAsBitmap(requireContext())
                        }

                        viewmodel.addToFavorites(movie)
                        (it as ImageButton).setImageResource(R.drawable.ic_baseline_favorite_24)
                        viewmodel.isFavorite = !viewmodel.isFavorite

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

}