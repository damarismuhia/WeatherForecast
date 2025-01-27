package com.dmuhia.weatherforecast.data.model
import com.dmuhia.weatherforecast.data.model.common.WeatherCondition
import com.dmuhia.weatherforecast.data.model.common.WeatherDetails
import com.dmuhia.weatherforecast.data.model.common.WindDetails
import com.google.gson.annotations.SerializedName

data class ForecastResponse (
    @SerializedName("cod")
    val cod: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("list")
    val list: List<ForecastDayWeatherDetails>,
    @SerializedName("city")
    val city: ForecastCityDetails
)
data class ForecastDayWeatherDetails(
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
data class ForecastCityDetails(
    @SerializedName("timezone")
    val timezone: Int,
    @SerializedName("name")
    val name: String,
)

