package com.example.netflex.di.component

import android.app.Application
import com.example.netflex.di.module.ViewModelModule
import com.example.netflex.ui.factory.MovieViewModelFactory
import dagger.BindsInstance
import dagger.Component

@Component(modules = [ViewModelModule::class])
interface FragmentComponent {
    fun getViewModelFactory(): MovieViewModelFactory

    @Component.Builder
    interface Builder{
        fun build(): FragmentComponent

        @BindsInstance
        fun app(app: Application): Builder
    }
}