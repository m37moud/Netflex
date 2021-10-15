package com.example.netflex.fragment

import com.example.netflex.databinding.FragmentMovieDetailsBinding
import com.example.netflex.fragment.base.BaseFragment

class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding>() {
    override var binding: FragmentMovieDetailsBinding?
    get() = FragmentMovieDetailsBinding.inflate(layoutInflater)
    set(value) {}

    override fun manipulateView(binding: FragmentMovieDetailsBinding?) {
    }
}