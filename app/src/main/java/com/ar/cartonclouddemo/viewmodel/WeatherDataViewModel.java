package com.ar.cartonclouddemo.viewmodel;

import android.util.Log;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.ar.cartonclouddemo.model.WeatherData;
import com.ar.cartonclouddemo.repository.WeatherRepository;
import com.ar.cartonclouddemo.utils.Resource;

import java.util.List;

public class WeatherDataViewModel extends ViewModel {

    private WeatherRepository weatherRepository;
    @ViewModelInject
    public WeatherDataViewModel(@Assisted SavedStateHandle handle,
                                WeatherRepository weatherRepository) {

       this.weatherRepository = weatherRepository;
    }


    public LiveData<Resource<List<WeatherData>>> loadWeatherData(String data){
        return this.weatherRepository.getWeatherData(data);
    }

    public LiveData<Resource<WeatherData>> getWeather(Long id){
        return this.weatherRepository.getWeather(id);
    }

}
