package com.ar.cartonclouddemo.remote


import javax.inject.Inject

class WeatherRemoteDataSource @Inject constructor(
    private val weatherService: WeatherService
) : BaseDataSource(){
    suspend fun getWeatherData(data: String) = getResult {

        weatherService.getWeatherData(data)
    }
}