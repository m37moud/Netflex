package com.example.netflex.di.component

import android.app.Application
import com.example.netflex.di.annotation.FragmentScope
import com.example.netflex.di.module.ViewModelModule
import com.example.netflex.ui.factory.MovieViewModelFactory
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@FragmentScope
@Component(modules = [ViewModelModule::class], dependencies = [AppComponent::class])
interface FragmentComponent {
    fun getViewModelFactory(): MovieViewModelFactory

    @Component.Builder
    interface Builder{
        fun build(): FragmentComponent

        fun appComponent(appComponent: AppComponent): Builder

        @BindsInstance
        fun app(app: Application): Builder
    }
}