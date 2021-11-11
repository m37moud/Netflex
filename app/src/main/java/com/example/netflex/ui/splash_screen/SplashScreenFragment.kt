package com.example.netflex.ui.splash_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.netflex.R
import com.example.netflex.databinding.FragmentSplashScreenBinding
import com.example.netflex.utils.executeAnimation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenFragment : Fragment() {
    private lateinit var binding: FragmentSplashScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashScreenBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    companion object {
        private const val NEXT_PAGE_DELAY = 3000L
        private const val BEFORE_ANIM_DELAY = 1000L
        private const val ANIMATION_DURATION = NEXT_PAGE_DELAY - BEFORE_ANIM_DELAY
    }
}