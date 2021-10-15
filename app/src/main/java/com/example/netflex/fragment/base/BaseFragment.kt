package com.example.netflex.fragment.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

open class BaseFragment<T>(
    private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> T
): Fragment() {
    private var mBinding: T? = null
    val binding get() = mBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = inflate(inflater, container, false)
        return (binding as ViewBinding).root
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }

}