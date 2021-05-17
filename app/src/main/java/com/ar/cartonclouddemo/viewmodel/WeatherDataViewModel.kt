package com.ar.cartonclouddemo.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.ar.cartonclouddemo.model.WeatherData
import com.ar.cartonclouddemo.repository.WeatherRepository
import com.ar.cartonclouddemo.utils.Resource

class WeatherDataViewModel @ViewModelInject constructor(
    @Assisted handle: SavedStateHandle?,
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    fun loadWeatherData(data: String?): LiveData<Resource<List<WeatherData>>> {
        return weatherRepository.getWeatherData(data!!)
    }

    fun getWeather(id: Long?): LiveData<Resource<WeatherData>> {
        return weatherRepository.getWeather(id!!)
    }
}