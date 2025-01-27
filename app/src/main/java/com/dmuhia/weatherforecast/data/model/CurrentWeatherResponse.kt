package com.dmuhia.weatherforecast.data.model
import com.dmuhia.weatherforecast.data.model.common.WeatherCondition
import com.dmuhia.weatherforecast.data.model.common.WeatherDetails
import com.dmuhia.weatherforecast.data.model.common.WindDetails
import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    @SerializedName("timezone")
    val timezone: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("dt")
    val dt: Int,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("weather")
    val weather: List<WeatherCondition>,
    @SerializedName("main")
    val main: WeatherDetails,
    @SerializedName("wind")
    val wind: WindDetails,
)





