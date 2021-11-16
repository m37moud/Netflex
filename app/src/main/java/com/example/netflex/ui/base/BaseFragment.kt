package com.example.netflex.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.netflex.app.MyApp
import com.example.netflex.di.component.DaggerFragmentComponent
import com.example.netflex.ui.factory.MovieViewModelFactory

abstract class BaseFragment<VB: ViewBinding, VM : ViewModel> : Fragment() {
    abstract val viewModelClass: Class<VM>
    protected abstract var viewModel: VM

    abstract val inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB
    abstract fun onBindViewModel(vm: VM)

    protected val binding: VB get() = mBinding!!
    private lateinit var viewModelFactory: MovieViewModelFactory
    private var mBinding: VB? = null

    private val componentBuilder = DaggerFragmentComponent.builder()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelFactory = componentBuilder
            .app(requireActivity().application)
            .appComponent((requireActivity().application as MyApp).appComponent)
            .build()
            .getViewModelFactory()
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
}