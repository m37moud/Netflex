package com.example.netflex.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.netflex.R
import com.example.netflex.databinding.FragmentSplashScreenBinding
import com.example.netflex.fragment.base.BaseFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenFragment: BaseFragment<FragmentSplashScreenBinding>(FragmentSplashScreenBinding::inflate) {
    companion object{
        const val NEXT_PAGE_DELAY = 3000L
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            delay(NEXT_PAGE_DELAY)
            findNavController().navigate(R.id.action_splashScreenFragment_to_movieCollectionFragment)
        }

    }
}