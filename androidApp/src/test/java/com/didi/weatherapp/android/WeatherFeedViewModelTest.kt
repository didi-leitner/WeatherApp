package com.didi.weatherapp.android

import com.didi.weatherapp.android.feed.WeatherFeedViewModel
import com.didi.weatherapp.android.testing.MainDispatcherRule
import com.didi.weatherapp.model.WeatherAlert
import com.didi.weatherapp.repository.fake.FakeWeatherAlertsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherFeedViewModelTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val weatherRepo = FakeWeatherAlertsRepository()

    private lateinit var viewModel: WeatherFeedViewModel

    @Before
    fun setup() {
        viewModel = WeatherFeedViewModel(repoAlerts = weatherRepo,)
        weatherRepo.sendWeatherAlert(FakeWeatherAlertsRepository.fakeAlerts)

    }

    @Test
    fun stateIsInitiallyLoading() = runTest {
        assertEquals(true, viewModel.isRefreshing.value)
        assertEquals(true, viewModel.wAlers.value.isEmpty())
    }

    @Test
    fun sixAlerts_showsInFeed() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.wAlers.collect() }

        val item = viewModel.wAlers.value
        assertEquals(item.size, 6)

        collectJob.cancel()
    }

    @Test
    fun pull_to_refresh_brings_new_item_from_network() = runTest {
        // Setup
        val wAlerts= mutableListOf<WeatherAlert>()

        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            var count = 0
            viewModel.wAlers.collect{

                if(it.isNotEmpty()){

                    wAlerts.addAll(it)

                    assertEquals(wAlerts[0], FakeWeatherAlertsRepository.fakeAlerts[0], )
                    assertEquals(wAlerts[1], FakeWeatherAlertsRepository.fakeAlerts[1])

                    //first state from DB
                    if(count == 0){
                        assertEquals(wAlerts.size, 6)
                    } else { //second state from network +1
                        assertEquals(wAlerts.size, 7)
                        assertEquals(wAlerts[6].event, "wildfire")

                    }
                    count++
                }

            }
        }

        collectJob.cancel()
    }

}


