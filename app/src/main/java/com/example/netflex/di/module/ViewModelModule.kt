package com.example.netflex.di.module

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.netflex.di.annotation.ViewModelKey
import com.example.netflex.ui.movie_collection_screen.MovieCollectionViewModel
import com.example.netflex.ui.movie_details_screen.MovieDetailsViewModel
import com.example.netflex.ui.splash_screen.SplashScreenViewModel
import com.example.netflex.ui.factory.MovieViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
class ViewModelModule {

    @Provides
    @IntoMap
    @ViewModelKey(SplashScreenViewModel::class)
    fun splashViewModel(): ViewModel{
        return SplashScreenViewModel()
    }

    @Provides
    @IntoMap
    @ViewModelKey(MovieCollectionViewModel::class)
    fun collectionViewmodel(app: Application): ViewModel{
        return MovieCollectionViewModel(app)
    }

    @Provides
    @IntoMap
    @ViewModelKey(MovieDetailsViewModel::class)
    fun detailsViewmodel(app: Application): ViewModel{
        return MovieDetailsViewModel(app)
    }

    @Provides
    @Singleton
    fun provideViewModelFactory(viewModelFactory: MovieViewModelFactory): ViewModelProvider.Factory{
        return viewModelFactory
    }

}