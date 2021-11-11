package com.example.netflex.di.module

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.netflex.di.annotation.FragmentScope
import com.example.netflex.di.annotation.ViewModelKey
import com.example.netflex.repository.MovieRepository
import com.example.netflex.ui.movie_collection_screen.MovieCollectionViewModel
import com.example.netflex.ui.movie_details_screen.MovieDetailsViewModel
import com.example.netflex.ui.factory.MovieViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class ViewModelModule {

    @Provides
    @IntoMap
    @ViewModelKey(MovieCollectionViewModel::class)
    fun collectionViewmodel(app: Application, repository: MovieRepository): ViewModel{
        return MovieCollectionViewModel(app, repository)
    }

    @Provides
    @IntoMap
    @ViewModelKey(MovieDetailsViewModel::class)
    fun detailsViewmodel(app: Application, repository: MovieRepository): ViewModel{
        return MovieDetailsViewModel(app, repository)
    }

    @Provides
    @FragmentScope
    fun provideViewModelFactory(viewModelFactory: MovieViewModelFactory): ViewModelProvider.Factory{
        return viewModelFactory
    }

}