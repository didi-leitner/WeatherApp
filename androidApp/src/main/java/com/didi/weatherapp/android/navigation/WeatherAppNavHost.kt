package com.didi.weatherapp.android.navigation


import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.didi.weatherapp.android.feed.WeatherFeedRoute
import com.didi.weatherapp.android.feed.WeatherFeedScreen

const val weatherFeedNavigationRoute = "weather_feed"

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WeatherAppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = weatherFeedNavigationRoute,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable(route = weatherFeedNavigationRoute) {
            WeatherFeedRoute()
        }
    }
}