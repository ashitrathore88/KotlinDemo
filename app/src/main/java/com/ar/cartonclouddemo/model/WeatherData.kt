package com.ar.cartonclouddemo.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class WeatherData(

    var predictability: Int,
    var visibility: Double,
    var humidity: Int,
    var air_pressure: Float,
    var wind_direction: Double,
    var wind_speed: Double,
    var the_temp: Float,
    var max_temp: Float,
    var min_temp: Float,
    var applicable_date: String,
    var created: String,
    var wind_direction_compass: String,
    var weather_state_abbr: String,
    var weather_state_name: String,
    @PrimaryKey var id: Long
)