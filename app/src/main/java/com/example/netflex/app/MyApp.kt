package com.example.netflex.app

import android.app.Application
import com.example.netflex.di.component.DaggerAppComponent

class MyApp: Application(){
    val appComponent = DaggerAppComponent.builder().app(this).build()
}