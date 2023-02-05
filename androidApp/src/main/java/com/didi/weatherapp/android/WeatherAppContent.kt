package com.didi.weatherapp.android

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import app.didi.ais.designsystem.GradientBackground
import app.didi.ais.designsystem.GradientColors
import com.didi.weatherapp.android.navigation.WeatherAppNavHost

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun WeatherAppContent(appState: WeatherAppState) {


    GradientBackground(
        gradientColors = GradientColors(
            colorResource(id = R.color.primaryLightColor),
            colorResource(R.color.primaryColor),
            Color.White)

    ) {

        Scaffold(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
        ) { padding ->
            Row(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .consumedWindowInsets(padding)
                    .windowInsetsPadding(
                        WindowInsets.safeDrawing.only(
                            WindowInsetsSides.Horizontal,
                        ),
                    ),
            ) {

                Column(Modifier.fillMaxSize()) {

                    WeatherAppNavHost(navController = appState.navController )

                }

            }
        }

    }
}