package com.capstone.diacare.di

import com.capstone.diacare.data.remote.retrofit.NewsService
import com.capstone.diacare.data.remote.retrofit.PredictionService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class NewsUrl

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class PredictUrl

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ChatUrl


    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()
    }


    @Provides
    @Singleton
    @PredictUrl
    fun provideRetrofitPrediction(client: OkHttpClient): PredictionService =
        Retrofit.Builder()
            .baseUrl("https://diacare-341172043706.asia-southeast2.run.app")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(PredictionService::class.java)

    @Provides
    @Singleton
    @NewsUrl
    fun provideRetrofitNews(client: OkHttpClient): NewsService =
        Retrofit.Builder()
            .baseUrl("https://backend-cc-543381820074.asia-southeast2.run.app")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(NewsService::class.java)


}