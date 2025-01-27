package com.dmuhia.weatherforecast.ui.screens.home.current

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.dmuhia.weatherforecast.R
import com.dmuhia.weatherforecast.utils.CurrentWeatherUiState
import com.dmuhia.weatherforecast.utils.Factory.capitalizeWords
import com.dmuhia.weatherforecast.utils.TemperatureUnit
import com.dmuhia.weatherforecast.utils.formatHumidityValue
import com.dmuhia.weatherforecast.utils.formatMamMinTemp
import com.dmuhia.weatherforecast.utils.formatPressureValue
import com.dmuhia.weatherforecast.utils.formatTemperatureValue
import com.dmuhia.weatherforecast.utils.formatWindValue

@Composable
fun CurrentLocationScreen(
   state: CurrentWeatherUiState,
    backgroundColor: Color =  MaterialTheme.colorScheme.primaryContainer,
    modifier: Modifier = Modifier){
   state.weather.let { data ->
       if (data != null)  {
           Card(
               modifier = modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp),
               shape = RoundedCornerShape(10.dp),
               colors = CardDefaults.cardColors(containerColor = backgroundColor)

           ){
               Column(
                   modifier = modifier
                       .fillMaxWidth()
                       .padding(start = 16.dp, top = 8.dp, end = 16.dp,bottom = 8.dp),
                   horizontalAlignment = Alignment.CenterHorizontally
               ) {
                   Text(
                       text = "Current Location: ${data.cityName}",
                       style = MaterialTheme.typography.titleMedium,
                       color =  MaterialTheme.colorScheme.onSurface
                   )
                   Text(
                       modifier = modifier.padding(top = 5.dp),
                       text = formatTemperatureValue(
                           temperature = data.temp
                       ),
                       style = MaterialTheme.typography.displaySmall,
                       color = MaterialTheme.colorScheme.onBackground
                   )
                   Row ( modifier = modifier.fillMaxWidth(),
                       horizontalArrangement = Arrangement.Center){
                       AsyncImage(
                           modifier = Modifier.size(42.dp),
                           model = stringResource(R.string.icon_image_url, "${data.icon}.png"),
                           contentDescription = null,
                           contentScale = ContentScale.Crop,
                           error = painterResource(id = R.drawable.art_clear),
                           placeholder = painterResource(id = R.drawable.art_clear),
                       )
                       Text(
                           modifier = modifier
                               .align(Alignment.CenterVertically)
                               .padding(start = 5.dp),
                           text = data.condition.capitalizeWords(),
                           style = MaterialTheme.typography.labelLarge,
                           color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),

                           )
                   }
                   Text(
                       modifier = modifier.padding(top = 5.dp),
                       text = formatMamMinTemp(
                           maxTemp = data.maxTemp ,
                           minTemp = data.minTemp
                       ),
                       style = MaterialTheme.typography.labelLarge,
                       color = MaterialTheme.colorScheme.onBackground
                   )

                   Row(
                       modifier = Modifier
                           .fillMaxWidth()
                           .padding(top = 20.dp),
                   ) {
                       Column(modifier = Modifier.weight(1f)) {
                           Image(
                               painterResource(id = R.drawable.ic_humidity),
                               contentDescription = stringResource(id = R.string.humidity_icon),
                               modifier = Modifier.fillMaxWidth(),
                               colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)

                           )
                           Text(
                               text = "${stringResource(id = R.string.humidity_label)}\n${formatHumidityValue(data.humidity)}",
                               color = MaterialTheme.colorScheme.onBackground,
                               fontSize = 15.sp,
                               modifier =
                               Modifier
                                   .fillMaxWidth()
                                   .padding(top = 8.dp),
                               textAlign = TextAlign.Center,
                           )
                       }
                       Column(modifier = Modifier.weight(1f)) {
                           Image(
                               painterResource(id = R.drawable.ic_pressure),
                               contentDescription = stringResource(id = R.string.pressure_icon),
                               modifier = Modifier.fillMaxWidth(),
                               colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                           )
                           Text(
                               text = "${stringResource(id = R.string.pressure)}\n${formatPressureValue(data.pressure)}",
                               color = MaterialTheme.colorScheme.onBackground,
                               fontSize = 15.sp,
                               modifier =
                               Modifier
                                   .fillMaxWidth()
                                   .padding(top = 8.dp),
                               textAlign = TextAlign.Center,
                           )
                       }
                       Column(modifier = Modifier.weight(1f)) {
                           Image(
                               painterResource(id = R.drawable.ic_wind),
                               contentDescription = stringResource(id = R.string.wind_speed_icon),
                               modifier = Modifier.fillMaxWidth(),
                               colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                           )
                           Text(
                               text = "${stringResource(id = R.string.wind_label)}\n${formatWindValue(data.windSpeed)}",
                               color = MaterialTheme.colorScheme.onBackground,
                               fontSize = 15.sp,
                               modifier =
                               Modifier
                                   .fillMaxWidth()
                                   .padding(top = 8.dp),
                               textAlign = TextAlign.Center,
                           )
                       }
                   }
               }
           }

       }
    }
}

