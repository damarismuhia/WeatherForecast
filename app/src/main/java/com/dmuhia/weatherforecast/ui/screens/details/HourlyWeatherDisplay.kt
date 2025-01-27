package com.dmuhia.weatherforecast.ui.screens.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dmuhia.weatherforecast.R
import com.dmuhia.weatherforecast.data.model.ForecastHourlyWeather
import com.dmuhia.weatherforecast.utils.Factory.formatDate
import com.dmuhia.weatherforecast.utils.TemperatureUnit
import com.dmuhia.weatherforecast.utils.formatTemperatureValue

@Composable
fun HourlyWeatherDisplay(
    weatherData: ForecastHourlyWeather,
    isCelsius: Boolean,
    modifier: Modifier = Modifier,
    textColor: Color = MaterialTheme.colorScheme.onSurface
) {
    val formattedTime = remember(weatherData) {
        weatherData.time.formatDate(from = "yyyy-MM-dd HH:mm", to = "HH:mm")
    }
    val temperature = if (isCelsius) {
        formatTemperatureValue(weatherData.temperatureC, TemperatureUnit.CELSIUS.displayName)
    } else {
        formatTemperatureValue(weatherData.temperatureF, TemperatureUnit.FAHRENHEIT.displayName)
    }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = formattedTime,
            color = textColor

        )
        AsyncImage(
            modifier = Modifier.size(42.dp),
            model = stringResource(R.string.icon_image_url, weatherData.icon),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.art_clear),
            placeholder = painterResource(id = R.drawable.art_clear),

       )
        Text(
            text = temperature,
            color = textColor,
            fontWeight = FontWeight.Bold
        )
    }
}