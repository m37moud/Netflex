package com.example.netflex.fragment

import com.example.netflex.databinding.FragmentMovieDetailsBinding
import com.example.netflex.fragment.base.BaseFragment
import com.example.netflex.fragment.viewmodel.MovieDetailsViewModel

class MovieDetailsFragment :
    BaseFragment<FragmentMovieDetailsBinding, MovieDetailsViewModel>
        (FragmentMovieDetailsBinding::inflate,
        MovieDetailsViewModel::class.java) {

}