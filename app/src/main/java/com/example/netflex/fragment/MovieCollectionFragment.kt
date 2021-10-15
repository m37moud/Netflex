package com.example.netflex.fragment

import com.example.netflex.databinding.FragmentMovieCollectionBinding
import com.example.netflex.fragment.base.BaseFragment

class MovieCollectionFragment : BaseFragment<FragmentMovieCollectionBinding>() {
    override var binding: FragmentMovieCollectionBinding?
    get() = FragmentMovieCollectionBinding.inflate(layoutInflater)
    set(value) {}

    override fun manipulateView(binding: FragmentMovieCollectionBinding?) {
    }
}