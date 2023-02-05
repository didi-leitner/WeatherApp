package com.didi.weatherapp.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            val navController = rememberNavController()
            val appState: WeatherAppState =  remember(navController) {
                WeatherAppState(navController)
            }
            WeatherAppContent(appState = appState)
        }


    }
}

@Stable
class WeatherAppState(
    val navController: NavHostController,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination
}



