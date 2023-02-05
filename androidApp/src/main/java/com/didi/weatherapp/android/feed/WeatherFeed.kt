package com.didi.weatherapp.android.feed

import android.app.Activity
import android.os.Build

import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.doOnPreDraw
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.didi.weatherapp.android.repository.fake.FakeWeatherAlertsRepository
import com.didi.weatherapp.model.WeatherAlert
import org.koin.androidx.compose.koinViewModel
import androidx.compose.foundation.lazy.grid.items




@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class,
    ExperimentalMaterialApi::class
)
@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun preview(){
    //RocketLaunchesRoute()
    WeatherFeedScreen(FakeWeatherAlertsRepository.fakeAlerts,false, {})
}
@OptIn(ExperimentalMaterialApi::class, ExperimentalLifecycleComposeApi::class)
@Composable
fun WeatherFeedRoute(viewModel: WeatherFeedViewModel = koinViewModel()) {

    val feedState by viewModel.wAlers.collectAsStateWithLifecycle()//LaunchesFeedUiState.Loading)

    //pull-to-refresh https://google.github.io/accompanist/swiperefresh/#usage
    val refreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()

    WeatherFeedScreen(weatherAlerts = feedState, refreshing, {viewModel.refresh()})

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WeatherFeedScreen(weatherAlerts: List<WeatherAlert>,
                      refreshing: Boolean,
                      refresh: ()->Unit,
                      pullRefreshState: PullRefreshState = rememberPullRefreshState(refreshing, { refresh() })
)
{

    println("TESTT ROCKET laucnh screen " + weatherAlerts)

    val isFeedLoading = refreshing//feedState is LaunchesFeedUiState.Loading

    // Workaround to call Activity.reportFullyDrawn from Jetpack Compose.
    // This code should be called when the UI is ready for use
    // and relates to Time To Full Display.
    // TODO replace with ReportDrawnWhen { } once androidx.activity-compose 1.7.0 is used (currently alpha)
    if (!isFeedLoading) {
        val localView = LocalView.current
        // We use Unit to call reportFullyDrawn only on the first recomposition,
        // however it will be called again if this composable goes out of scope.
        // Activity.reportFullyDrawn() has its own check for this
        // and is safe to call multiple times though.
        LaunchedEffect(Unit) {
            // We're leveraging the fact, that the current view is directly set as content of Activity.
            val activity = localView.context as? Activity ?: return@LaunchedEffect
            // To be sure not to call in the middle of a frame draw.
            localView.doOnPreDraw { activity.reportFullyDrawn() }
        }
    }

    val scrollableState = rememberLazyGridState()
    Box(Modifier.pullRefresh(pullRefreshState)) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(300.dp),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier
                .fillMaxSize(),
            state = scrollableState,
        ) {

            wAlertsFeed(alertList = weatherAlerts)

        }

        PullRefreshIndicator(refreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
    }

}

fun LazyGridScope.wAlertsFeed(alertList: List<WeatherAlert> ) {

    items(items = alertList, key = { it.id}) { wAlert ->

        WeatherAlertCardExpanded(
            alert = wAlert, onClick = {
                //TODO?
            }
        )
    }
}





/**
 * A sealed class to make dealing with [ImageVector] and [DrawableRes] icons easier.
 */
sealed class Icon {
    data class ImageVectorIcon(val imageVector: ImageVector) : Icon()
    data class DrawableResourceIcon(@DrawableRes val id: Int) : Icon()
}
