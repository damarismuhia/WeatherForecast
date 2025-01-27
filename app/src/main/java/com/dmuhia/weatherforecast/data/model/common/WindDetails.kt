package com.dmuhia.weatherforecast.data.model.common

import com.google.gson.annotations.SerializedName

data class WindDetails (
    @SerializedName("speed")
    val speed: Double,
)