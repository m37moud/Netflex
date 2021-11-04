package com.example.netflex.ui.splash_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.netflex.R
import com.example.netflex.databinding.FragmentSplashScreenBinding
import com.example.netflex.ui.base.BaseFragment
import com.example.netflex.utils.executeAnimation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenFragment : BaseFragment<FragmentSplashScreenBinding, SplashScreenViewModel>() {
    override lateinit var viewModel: SplashScreenViewModel
    override val viewModelClass: Class<SplashScreenViewModel>
        get() = SplashScreenViewModel::class.java

    override fun onBindViewModel(viewmodel: SplashScreenViewModel) {
        viewLifecycleOwner.lifecycleScope.launch {
            delay(BEFORE_ANIM_DELAY)

            with(binding) {
                logo?.executeAnimation(
                    requireContext(),
                    R.anim.splash_to_collection_anim_logo,
                    ANIMATION_DURATION
                )
                appName?.executeAnimation(
                    requireContext(),
                    R.anim.splash_to_collection_anim_name,
                    ANIMATION_DURATION
                )
            }

            delay(ANIMATION_DURATION)

            findNavController().navigate(R.id.action_splashScreenFragment_to_movieCollectionFragment)
        }
    }

    override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSplashScreenBinding
        get() = FragmentSplashScreenBinding::inflate

    companion object {
        private const val NEXT_PAGE_DELAY = 3000L
        private const val BEFORE_ANIM_DELAY = 1000L
        private const val ANIMATION_DURATION = NEXT_PAGE_DELAY - BEFORE_ANIM_DELAY
    }
}