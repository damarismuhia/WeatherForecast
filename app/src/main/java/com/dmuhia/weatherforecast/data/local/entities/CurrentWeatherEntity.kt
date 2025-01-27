package com.dmuhia.weatherforecast.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_weather")
data class CurrentWeatherEntity(
    @PrimaryKey
    val cityName: String,
    val visibility: Int,
    val condition: String,
    val icon: String,
    val temp:Double,
    val maxTemp:Double,
    val minTemp:Double,
    val humidity: Int,
    val pressure: Int,
    val windSpeed: Double,
    val dt: String,
)



