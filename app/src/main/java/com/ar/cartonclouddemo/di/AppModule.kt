package com.ar.cartonclouddemo.di

import android.content.Context
import com.ar.cartonclouddemo.remote.WeatherRemoteDataSource
import com.ar.cartonclouddemo.remote.WeatherService
import com.ar.cartonclouddemo.repository.WeatherRepository
import com.ar.cartonclouddemo.room.AppDatabase
import com.ar.cartonclouddemo.room.WeatherDataDao
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit

import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl("https://www.metaweather.com/api/location/")
        .addConverterFactory(GsonConverterFactory.create(gson))

        .build()

    @Singleton
    @Provides
    fun provideWeatherService(retrofit: Retrofit): WeatherService = retrofit.create(WeatherService::class.java);

    @Singleton
    @Provides
    fun provideWeatherRemoteSource(weatherService: WeatherService) = WeatherRemoteDataSource(weatherService)


    @Singleton
    @Provides
    fun provideWeatherRepository(
        remoteDataSoure: WeatherRemoteDataSource,
        localDataSource: WeatherDataDao
    ) = WeatherRepository(remoteDataSoure,localDataSource)


    @Singleton
    @Provides
    fun provideWeatherDataDao(dB: AppDatabase) = dB.weatherDataDao()
}