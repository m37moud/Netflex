package com.example.netflex.di.component

import android.app.Application
import com.example.netflex.app.MyApp
import com.example.netflex.di.module.DatabaseModule
import com.example.netflex.di.module.RetrofitModule
import com.example.netflex.di.module.ViewModelModule
import com.example.netflex.fragment.viewmodel.factory.MovieViewModelFactory
import com.example.netflex.repository.MovieRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModule::class, DatabaseModule::class])
interface AppComponent {

    fun getMovieRepository(): MovieRepository

    @Component.Builder
    interface Builder{
        fun build(): AppComponent

        @BindsInstance
        fun app(app: MyApp): Builder
    }
}