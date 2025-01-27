package com.dmuhia.weatherforecast.ui.screens.home.forecast

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.toggleableState
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dmuhia.weatherforecast.R
import com.dmuhia.weatherforecast.domain.ForecastWeather
import com.dmuhia.weatherforecast.ui.components.ConditionsLabelSection
import com.dmuhia.weatherforecast.ui.components.ExpandItemButton
import com.dmuhia.weatherforecast.ui.components.ImageLabelComponent
import com.dmuhia.weatherforecast.utils.Factory.capitalizeWords
import com.dmuhia.weatherforecast.utils.ForecastWeatherUiState
import com.dmuhia.weatherforecast.utils.formatHumidityValue
import com.dmuhia.weatherforecast.utils.formatPressureValue
import com.dmuhia.weatherforecast.utils.formatTemperatureValue
import com.dmuhia.weatherforecast.utils.formatWindValue
@Composable
fun ForecastContentScreen(
    forecastItem: ForecastWeatherUiState,
    modifier: Modifier = Modifier
) {
    var expandedDate by remember { mutableStateOf<ForecastWeather?>(null) }
    LazyColumn(modifier = modifier.fillMaxHeight()
        .padding(start = 16.dp)) {
        item {
            ImageLabelComponent(modifier.padding( top = 8.dp),R.drawable.ic_calendar,
                (R.string.day_forecast),forecastItem.forecast?.cityName)
        }
        forecastItem.forecast?.forecastWeatherDetails?.let { forecast ->
            items(forecast) { item ->
                ForecastCell(
                    forecastDetails = item,
                    expanded = expandedDate == item,
                    onClick = {
                        expandedDate = if (expandedDate == item) null else item
                    },
                    modifier = Modifier.animateItem(fadeInSpec = null, fadeOutSpec = null)
                )
            }
        }
    }
}

@Composable
fun ForecastCell(
    forecastDetails: ForecastWeather,
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        shape = MaterialTheme.shapes.small,
        modifier = modifier
            .padding(vertical = 4.dp, horizontal = 4.dp)
            .animateContentSize()
            .semantics {
                toggleableState = if (expanded) ToggleableState.On else ToggleableState.Off
            }
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 5.dp)
                .clickable {
                    onClick()
                }
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = modifier
            ) {
                Text(
                    text = forecastDetails.dt,
                    modifier.padding(bottom = 5.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color =  MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = forecastDetails.condition.capitalizeWords(),
                    style = MaterialTheme.typography.bodySmall,
                    color =  MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            AsyncImage(
                modifier = Modifier.size(48.dp),
                model = stringResource(R.string.icon_image_url, "${forecastDetails.icon}.png"),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.art_clear),
                placeholder = painterResource(id = R.drawable.art_clear),
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.padding(vertical = 8.dp)
            ) {
                Text(
                    text = formatTemperatureValue(forecastDetails.maxTemp),
                    modifier.padding(bottom = 5.dp),
                    style = MaterialTheme.typography.labelLarge,
                    color =  MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = formatTemperatureValue(forecastDetails.minTemp),
                    style = MaterialTheme.typography.labelSmall,
                    color =  MaterialTheme.colorScheme.onSurface
                )
            }
            ExpandItemButton(
                expanded = expanded,
                onClick = onClick
            )
        }
        if (expanded) {
            Column(modifier = modifier.padding(horizontal = 5.dp, vertical = 2.dp)) {
                ConditionsLabelSection(modifier, R.drawable.ic_wind, R.string.wind_label, value = formatWindValue(forecastDetails.windSpeed))
                ConditionsLabelSection(modifier, R.drawable.ic_humidity, R.string.humidity_label, value = formatHumidityValue(forecastDetails.humidity))
                ConditionsLabelSection(modifier, R.drawable.ic_visibility, R.string.visibility, value = "${forecastDetails.visibility} m")
                ConditionsLabelSection(modifier, R.drawable.ic_pressure, R.string.pressure, value = formatPressureValue(forecastDetails.pressure))
            }
        }
    }
}

