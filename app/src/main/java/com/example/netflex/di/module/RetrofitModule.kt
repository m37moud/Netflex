package com.example.netflex.di.module

import com.example.netflex.retrofit.MovieApi
import com.example.netflex.retrofit.RetrofitConstants.BASE_URL
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RetrofitModule {

    @Singleton
    @Provides
    fun provideRetrofitInstance(): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.addInterceptor {
            val url = it.request().url().newBuilder()
                .addQueryParameter("api_key", "4d3ec680a0d9e9167e276f5571bae754")
                .addQueryParameter("language", "en-US")
            it.proceed(it.request().newBuilder().url(url.build()).build())
        }

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieApi(retrofit: Retrofit): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }

}