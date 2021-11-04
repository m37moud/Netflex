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
    abstract val viewModelClass: Class<VM>
    protected abstract var viewModel: VM

    protected val binding: VB get() = mBinding!!
    private lateinit var viewModelFactory: MovieViewModelFactory
    private var mBinding: VB? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelFactory = DaggerFragmentComponent
            .builder()
            .app(requireActivity().application)
            .build().getViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(viewModelClass)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = inflate(inflater, container, false)
        return mBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBindViewModel(viewModel)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

    abstract val inflate: (layoutInflater: LayoutInflater, viewGroup: ViewGroup?, attachToRoot: Boolean) -> VB

    abstract fun onBindViewModel(viewmodel: VM)
}