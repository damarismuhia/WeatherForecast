package com.dmuhia.weatherforecast.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dmuhia.weatherforecast.ui.screens.home.HomeScreen
import com.dmuhia.weatherforecast.viewmodel.WeatherViewModel


@Composable
fun WeatherAppNavigation(
    viewModel: WeatherViewModel,
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(viewModel)
        }
    }
}
