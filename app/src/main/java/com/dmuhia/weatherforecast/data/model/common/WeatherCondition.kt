package com.dmuhia.weatherforecast.data.model.common

import com.google.gson.annotations.SerializedName

data class WeatherCondition(
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String
)