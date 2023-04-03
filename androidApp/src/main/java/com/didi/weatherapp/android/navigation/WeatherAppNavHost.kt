package com.didi.weatherapp.android.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.didi.weatherapp.android.details.AlertDetailsRoute
import com.didi.weatherapp.android.details.AlertDetailsViewModel
import com.didi.weatherapp.android.feed.WeatherFeedRoute
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

const val weatherFeedNavigationRoute = "weather_feed"
const val alertDetailsRoute = "alert_details/{alertId}"

@Composable
fun WeatherAppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = weatherFeedNavigationRoute,
) {

    val onNavigateToAlertDetail: (id:String) -> Unit = {
        println("TESTT navigate to " + it)
        navController.navigate(
            alertDetailsRoute
                .replace(
                    oldValue = "{alertId}",
                    newValue = it
                )
        )
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable(route = weatherFeedNavigationRoute) {
            WeatherFeedRoute(onItemClicked = onNavigateToAlertDetail)
        }


        composable(route = alertDetailsRoute) { navBackStackEntry ->
            val alertId = navBackStackEntry.arguments?.getString("alertId")
            alertId?.let {
                val viewModel = getViewModel<AlertDetailsViewModel>(
                    parameters = { parametersOf(alertId) }
                )
                AlertDetailsRoute(viewModel)
            }
        }


    }
}