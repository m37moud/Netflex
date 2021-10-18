package com.example.netflex.di

import com.example.netflex.di.module.RetrofitModule
import com.example.netflex.fragment.MovieCollectionFragment
import com.example.netflex.retrofit.MovieApi
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModule::class])
interface RetrofitComponent {

    fun getMovieApi(): MovieApi

}