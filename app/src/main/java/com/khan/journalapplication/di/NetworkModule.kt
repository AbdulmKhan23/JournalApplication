package com.khan.journalapplication.di

import com.khan.journalapplication.data.remote.JournalApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "http://10.0.2.2:8000/"// check url

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit { return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build() }

    @Provides
    @Singleton
    fun provideJournalApi(
        retrofit: Retrofit
    ): JournalApi {
        return retrofit.create(JournalApi::class.java)
    }
}
