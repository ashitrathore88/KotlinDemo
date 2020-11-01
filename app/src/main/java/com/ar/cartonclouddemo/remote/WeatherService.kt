package com.ar.cartonclouddemo.remote

import com.ar.cartonclouddemo.model.WeatherData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherService {
    //@GET("1100661/2020/10/30/")
    @GET("{data}")
    suspend fun getWeatherData(@Path("data") data:String): Response<List<WeatherData>>
}