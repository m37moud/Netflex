package com.example.netflex.app

import android.app.Application
import com.example.netflex.di.component.DaggerAppComponent
import com.example.netflex.retrofit.RetrofitConstants

class MyApp: Application(){
    val appComponent = DaggerAppComponent
        .builder()
        .app(this)
        .baseUrl(RetrofitConstants.BASE_URL)
        .build()
}