package com.example.netflex.fragment.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.netflex.di.component.DaggerFragmentComponent
import com.example.netflex.fragment.viewmodel.factory.MovieViewModelFactory

abstract class BaseFragment<VB: ViewBinding, VM : ViewModel> : Fragment() {

    private lateinit var viewModelFactory: MovieViewModelFactory
    private var mBinding: VB? = null
    private lateinit var viewmodel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelFactory = DaggerFragmentComponent
            .builder()
            .app(requireActivity().application)
            .build().getViewModelFactory()
        viewmodel = ViewModelProvider(this, viewModelFactory).get(viewModelClass)
        onBindViewModel(viewmodel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = inflate(inflater, container, false)
        initView(mBinding!!)
        return mBinding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

    abstract fun inflate(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, attachToRoot: Boolean): VB

    abstract fun initView(binding: VB)

    abstract fun onBindViewModel(viewModel: VM)

    abstract val binding: VB

    abstract val viewModelClass: Class<VM>

    abstract val viewModel: VM
}