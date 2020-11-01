package com.ar.cartonclouddemo.repository


import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.ar.cartonclouddemo.remote.WeatherRemoteDataSource
import com.ar.cartonclouddemo.room.WeatherDataDao
import com.ar.cartonclouddemo.utils.Resource
import com.ar.cartonclouddemo.utils.performGetOperation
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val remoteDataSoure: WeatherRemoteDataSource,
    private val localDataSource: WeatherDataDao
) {

    fun getWeatherData(data:String) = performGetOperation(
        databaseQuery = { localDataSource.getWeatherData() },
        networkCall = { remoteDataSoure.getWeatherData(data) },
        saveCallResult = { localDataSource.insertWeather(it) }
    )

    fun getWeather(id: Long)  = liveData(Dispatchers.IO){
        val source = localDataSource.getWeatherDetail(id).map { Resource.success(it) }
        emitSource(source)
    }

}