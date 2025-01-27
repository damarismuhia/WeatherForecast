package com.dmuhia.weatherforecast.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.dmuhia.weatherforecast.domain.ForecastWeather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


@Entity(tableName = "forecast_weather")
data class ForecastWeatherEntity(
    @PrimaryKey
    val cityName: String,
    val weather: List<ForecastWeather>,
)



class ForecastWeatherConverter {

    @TypeConverter
    fun fromForecastWeatherList(forecastList: List<ForecastWeather>?): String? {
        return Gson().toJson(forecastList) // Convert list to JSON string
    }

    @TypeConverter
    fun toForecastWeatherList(forecastListString: String?): List<ForecastWeather>? {
        val listType = object : TypeToken<List<ForecastWeather>>() {}.type
        return Gson().fromJson(forecastListString, listType) // Convert JSON string back to list
    }
}


