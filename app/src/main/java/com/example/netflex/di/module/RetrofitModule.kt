package com.example.netflex.di.module

import com.example.netflex.retrofit.MovieApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class RetrofitModule {

    @Singleton
    @Provides
    fun provideRetrofitInstance(@Named("baseUrl")baseUrl: String,
                                httpClient: OkHttpClient,
                                converterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieApi(retrofit: Retrofit): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }

    @Provides
    fun provideHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.addInterceptor {
            val url = it.request().url().newBuilder()
                .addQueryParameter("api_key", "4d3ec680a0d9e9167e276f5571bae754")
                .addQueryParameter("language", "en-US")
            it.proceed(it.request().newBuilder().url(url.build()).build())
        }
        return okHttpClient.build()
    }

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory{
        return GsonConverterFactory.create()
    }
}