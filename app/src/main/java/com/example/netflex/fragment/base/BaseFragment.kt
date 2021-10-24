package com.example.netflex.fragment.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T, V: ViewModel>(
    private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> T,
    private val VM: Class<V>
): Fragment() {
    private var mBinding: T? = null
    val binding get() = mBinding
    protected lateinit var viewmodel: V

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewmodel = ViewModelProvider(this).get(VM)
        mBinding = inflate(inflater, container, false)
        return (binding as ViewBinding).root
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }

}