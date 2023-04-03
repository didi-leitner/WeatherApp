package com.didi.weatherapp.android.testing.fakeRepositories

import com.didi.weatherapp.model.WeatherAlert
import com.didi.weatherapp.network.dto.ZoneNO
import com.didi.weatherapp.repository.fake.FakeWeatherAlertsRepository
import com.didi.weatherapp.repository.interfaces.IWeatherAlertsRepository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.Clock

class FakeWeatherAppRepository: IWeatherAlertsRepository {

    companion object {

        private val affectedZones = listOf<String>("https://api.weather.gov/zones/forecast/PKZ750",
            "https://api.weather.gov/zones/forecast/PKZ777",
            "https://api.weather.gov/zones/forecast/PKZ767",
            "https://api.weather.gov/zones/forecast/PKZ772"
        )
        private val date = Clock.System.now().toString()

        private val l1 = WeatherAlert("id1","hurricane", date,
            date, "sender1", null, "Severe","Likely","Expected", affectedZones.subList(0,2), null )
        private val l2 = WeatherAlert("id2","fog",
            date,
            date, "Sender Two", null, "Minor","Likely","Expected", affectedZones.subList(2,3),  "instructions blablabla")
        private val l3 = WeatherAlert("id3","heavy snowfall",
            date,
            date, "Sender Three", "description3", "Severe", "Likely", "Expected", affectedZones.subList(0,1), null)
        private val l4 = WeatherAlert("id4","tsunami",
            date,
            date, "GOV", "description4", "Severe", "Likely", "Expected", affectedZones.subList(0,1), null)
        private val l5 = WeatherAlert("id5","volcano explosion",
            date,
            date, "Random sender", "description5", "Severe", "Likely", "Expected", affectedZones.subList(0,1), null)
        private val l6 = WeatherAlert("id6","earthquake",
            date,
            date, "sndr", "description6", "Severe", "Likely", "Expected", affectedZones.subList(0,1), null)




        val fakeAlerts = listOf<WeatherAlert>(l1, l2, l3, l4, l5, l6)
    }


    private val wAlertsFlow: MutableSharedFlow<List<WeatherAlert>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override fun getAlertFromDB(id: String) = flowOf(FakeWeatherAlertsRepository.fakeAlerts.find { it.id == id })


    override fun getAlertsFromDB() = wAlertsFlow


    override suspend fun refreshAlertsFromAPI(): List<WeatherAlert> {
        delay(1000)
        val l7 = WeatherAlert("id7","wildfire", date, date, "sndr", "description6", "Severe", "Likely", "Expected", affectedZones.subList(0,1), null)
        val list = fakeAlerts.toMutableList()
            list.add(l7)

        return list

    }

    override suspend fun getAllAlertsIOS(): List<WeatherAlert> {
        TODO("Not yet implemented")
    }

    override suspend fun getZoneInfoFromAPI(zoneCode: String): ZoneNO? {
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