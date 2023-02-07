package com.didi.weatherapp.android.testing.fakeRepositories

import com.didi.weatherapp.model.WeatherAlert
import com.didi.weatherapp.repository.interfaces.IWeatherAlertsRepository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.datetime.Clock

class FakeWeatherAppRepository: IWeatherAlertsRepository {

    companion object {
        private val date = Clock.System.now().toString()

        private val l1 = WeatherAlert("id1","hurricane", date, date, "sender1")
        private val l2 = WeatherAlert("id2","fog", date, date, "Sender Two")
        private val l3 = WeatherAlert("id3","heavy snowfall", date, date, "Sender Three")
        private val l4 = WeatherAlert("id4","tsunami", date, date, "GOV")
        private val l5 = WeatherAlert("id5","volcano explosion", date, date, "Random sender")
        private val l6 = WeatherAlert("id6","earthquake", date, date, "sndr")


        val fakeAlerts = listOf<WeatherAlert>(l1, l2, l3, l4, l5, l6)
    }


    private val wAlertsFlow: MutableSharedFlow<List<WeatherAlert>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override fun getAlertsFromDB() = wAlertsFlow


    override suspend fun refreshLaunchesFromAPI(): List<WeatherAlert> {
        delay(1000)
        val l7 = WeatherAlert("id7","wildfire", date, date, "sndr")
        val list = fakeAlerts.toMutableList()
            list.add(l7)

        return list

    }

    override suspend fun getAllLaunchesOld(): List<WeatherAlert> {
        TODO("Not yet implemented")
    }

    fun sendWeatherAlert(wAlerts: List<WeatherAlert>) {
        wAlertsFlow.tryEmit(wAlerts)
    }
}

/*
*    /**
     * The backing hot flow for the list of topics ids for testing.
     */
    private val newsResourcesFlow: MutableSharedFlow<List<NewsResource>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override fun getNewsResources(): Flow<List<NewsResource>> = newsResourcesFlow

    override fun getNewsResources(filterTopicIds: Set<String>): Flow<List<NewsResource>> =
        getNewsResources().map { newsResources ->
            newsResources.filter {
                it.topics.map(Topic::id).intersect(filterTopicIds).isNotEmpty()
            }
        }

    /**
     * A test-only API to allow controlling the list of news resources from tests.
     */
    fun sendNewsResources(newsResources: List<NewsResource>) {
        newsResourcesFlow.tryEmit(newsResources)
    }

    override suspend fun syncWith(synchronizer: Synchronizer) = true
*
* */