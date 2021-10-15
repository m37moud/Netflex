package com.example.netflex.fragment

import android.os.Handler
import android.os.Looper
import androidx.navigation.fragment.findNavController
import com.example.netflex.R
import com.example.netflex.databinding.FragmentSplashScreenBinding
import com.example.netflex.fragment.base.BaseFragment

class SplashScreenFragment : BaseFragment<FragmentSplashScreenBinding>() {

    override var binding: FragmentSplashScreenBinding?
    get() = FragmentSplashScreenBinding.inflate(layoutInflater)
    set(value) {}

    override fun manipulateView(binding: FragmentSplashScreenBinding?) {

        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.action_splashScreenFragment_to_movieCollectionFragment)
        }, 3000)

    }

}