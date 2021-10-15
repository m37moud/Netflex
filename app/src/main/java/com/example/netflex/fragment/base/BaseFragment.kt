package com.example.netflex.fragment.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T>: Fragment() {
    protected abstract var binding: T?
    private var mBinding: T? = null

    protected abstract fun manipulateView(binding: T?)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mBinding = binding
        manipulateView(mBinding)
        return (mBinding as ViewBinding).root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        mBinding = null
    }
}