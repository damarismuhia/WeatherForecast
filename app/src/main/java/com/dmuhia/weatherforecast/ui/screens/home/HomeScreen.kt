package com.dmuhia.weatherforecast.ui.screens.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.dmuhia.weatherforecast.R
import com.dmuhia.weatherforecast.ui.components.ImageLabelComponent
import com.dmuhia.weatherforecast.ui.components.WeatherErrorState
import com.dmuhia.weatherforecast.ui.screens.home.current.CurrentLocationScreen
import com.dmuhia.weatherforecast.ui.screens.home.forecast.ForecastContentScreen
import com.dmuhia.weatherforecast.utils.CurrentWeatherUiState
import com.dmuhia.weatherforecast.utils.ForecastWeatherUiState
import com.dmuhia.weatherforecast.viewmodel.WeatherViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
@Composable
fun HomeScreen(viewModel: WeatherViewModel, modifier: Modifier = Modifier){
    var city by remember { mutableStateOf("") }
    val currentWeatherState by viewModel.currentWeatherDetails.collectAsState()
    val forecastWeatherState by viewModel.forecastUIState.collectAsState()
    val location = viewModel.location.collectAsState()
    val context = LocalContext.current
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    val permissionState = remember { mutableStateOf(false) }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            permissionState.value = granted
            if (granted) {
                fetchLocationAndWeather(fusedLocationClient, viewModel)
            } else {
                Toast.makeText(context, "Location permission is required to get weather data.", Toast.LENGTH_LONG).show()
            }
        }
    )
    LaunchedEffect(Unit) {
        when {
            context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED -> {
                permissionState.value = true
                fetchLocationAndWeather(fusedLocationClient, viewModel)
            }
            else -> requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }
    Scaffold(modifier = modifier.fillMaxSize(),
        topBar = { HomeTopBar(onRefreshClicked = {
            fetchLocationAndWeather(fusedLocationClient, viewModel)
        })}
    ) { paddingValues ->
        Surface(color = MaterialTheme.colorScheme.surface,
            modifier = modifier.fillMaxSize()) {
            Column(modifier = modifier.fillMaxSize()
                .padding(paddingValues)) {
                if(currentWeatherState.weather?.dt != null){
                    Text(
                        modifier = modifier.padding(start = 16.dp),
                        text = "Last Updated On: ${currentWeatherState.weather?.dt}",
                    )
                    SearchBar(
                        modifier,
                        query = city,
                        onQueryChange = {
                                newQuery->  city = newQuery
                        },
                        onSearch = {
                            if (city.isNotEmpty()) {
                                viewModel.fetchWeatherForCity(city.trim())
                                city = ""
                            }else{
                                Toast.makeText(context, "Please enter a city.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                }

                WeatherScreenContent(currentWeatherState,viewModel,location.value?.latitude,location.value?.latitude)
                ForecastWeatherScreenContent(forecastWeatherState,viewModel)
            }

        }
    }
}

@SuppressLint("MissingPermission")
private fun fetchLocationAndWeather(
    fusedLocationClient: FusedLocationProviderClient,
    viewModel: WeatherViewModel) {
    fusedLocationClient.lastLocation.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val location: Location? = task.result
            location?.let {
                viewModel.updateLocation(it.latitude,it.longitude)
                viewModel.fetchCurrentWeatherData(lat = it.latitude, log = it.longitude)
            }
        }
    }
}


@Composable
fun HomeTopBar(
    onRefreshClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(onClick = { onRefreshClicked() }) {
            Icon(
                imageVector = Icons.Rounded.Refresh,
                contentDescription = stringResource(R.string.home_content_description_refresh_icon)
            )
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,

) {
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        value = query,
        onValueChange = { onQueryChange(it) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch()
                keyboardController?.hide()
            }

        ),
        placeholder = { Text(text = "Search for a city") },
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = Color.Gray
            )
        },
        shape = MaterialTheme.shapes.medium,
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier.fillMaxWidth()
            .padding(start = 16.dp, top = 8.dp, end = 16.dp)
    )
}

@Composable
fun WeatherScreenContent(
    uiState: CurrentWeatherUiState,
    viewModel: WeatherViewModel,
    lat:Double?,
    lon:Double?
) {
    when {
        uiState.isLoading -> {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        }

        uiState.errorMessage != null -> {
            WeatherErrorState(error = uiState.errorMessage) {
               // viewModel.fetchCurrentWeatherData(lat = lat, log = lon)
            }
        }

        else -> {
            if (uiState.weather != null) {
                CurrentLocationScreen(uiState,  MaterialTheme.colorScheme.primaryContainer)
                viewModel.fetchWeatherForCity(uiState.weather.cityName)
            } else {
                WeatherErrorState(error = "No weather data available") {
                   // viewModel.fetchCurrentWeatherData(lat = lat, log = lon)
                }
            }
        }
    }
}
@Composable
fun ForecastWeatherScreenContent(
    state: ForecastWeatherUiState,
    viewModel: WeatherViewModel,
) {
    when {
        state.isLoading -> {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        }

        state.errorMessage != null -> {
            WeatherErrorState(error = state.errorMessage) {

            }
        }

        else -> {
            if (state.forecast != null) {
                ForecastContentScreen(state)

            } else {
                WeatherErrorState(error = "No weather data available") {
                    // viewModel.fetchCurrentWeatherData(lat = lat, log = lon)
                }
            }
        }
    }
}


