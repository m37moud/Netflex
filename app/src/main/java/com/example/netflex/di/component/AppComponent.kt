package com.example.netflex.di.component

import com.example.netflex.di.module.RetrofitModule
import com.example.netflex.di.module.ViewModelModule
import com.example.netflex.fragment.viewmodel.factory.MovieViewModelFactory
import com.example.netflex.retrofit.MovieApi
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModule::class, ViewModelModule::class])
interface AppComponent {

    fun getMovieApi(): MovieApi

    fun getViewModelFactory(): MovieViewModelFactory

}