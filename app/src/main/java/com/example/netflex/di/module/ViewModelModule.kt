package com.example.netflex.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.netflex.di.annotation.ViewModelKey
import com.example.netflex.fragment.viewmodel.MovieCollectionViewModel
import com.example.netflex.fragment.viewmodel.MovieDetailsViewModel
import com.example.netflex.fragment.viewmodel.SplashScreenViewModel
import com.example.netflex.fragment.viewmodel.factory.MovieViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SplashScreenViewModel::class)
    fun splashViewModel(viewModel: SplashScreenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieCollectionViewModel::class)
    fun collectionViewmodel(viewModel: MovieCollectionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailsViewModel::class)
    fun detailsViewmodel(viewModel: MovieDetailsViewModel): ViewModel

    @Binds
    @Singleton
    fun bindViewModelFactory(viewModelFactory: MovieViewModelFactory): ViewModelProvider.Factory

}