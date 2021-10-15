package com.example.netflex.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.netflex.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenFragment : Fragment(R.layout.fragment_splash_screen) {
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