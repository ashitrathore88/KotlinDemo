package com.ar.cartonclouddemo.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ar.cartonclouddemo.model.WeatherData

@Dao
interface WeatherDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(locations: List<WeatherData>)

    @Query("select * from weatherdata ORDER BY created DESC")
    fun getWeatherData(): LiveData<List<WeatherData>>

    @Query("select * from weatherdata where id = :id")
    fun getWeatherDetail(id: Long): LiveData<WeatherData>
}