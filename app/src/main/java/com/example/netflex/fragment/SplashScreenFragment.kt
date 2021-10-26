package com.example.netflex.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.netflex.R
import com.example.netflex.databinding.FragmentSplashScreenBinding
import com.example.netflex.fragment.base.BaseFragment
import com.example.netflex.fragment.viewmodel.SplashScreenViewModel
import com.example.netflex.utils.executeAnimation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenFragment: BaseFragment<FragmentSplashScreenBinding, SplashScreenViewModel>
    (FragmentSplashScreenBinding::inflate,
    SplashScreenViewModel::class.java) {
    companion object{
        private const val NEXT_PAGE_DELAY = 3000L
        private const val BEFORE_ANIM_DELAY = 1000L
        private const val ANIMATION_DURATION = NEXT_PAGE_DELAY - BEFORE_ANIM_DELAY
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            delay(BEFORE_ANIM_DELAY)

            binding!!.logo?.executeAnimation(requireContext(), R.anim.splash_to_collection_anim_logo, ANIMATION_DURATION)
            binding!!.appName?.executeAnimation(requireContext(), R.anim.splash_to_collection_anim_name, ANIMATION_DURATION)

            delay(ANIMATION_DURATION)

            findNavController().navigate(R.id.action_splashScreenFragment_to_movieCollectionFragment)
        }

    }
}