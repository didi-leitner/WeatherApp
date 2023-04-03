package com.didi.weatherapp.repository.fake



import com.didi.weatherapp.model.WeatherAlert
import com.didi.weatherapp.network.dto.ZoneNO
import com.didi.weatherapp.repository.interfaces.IWeatherAlertsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.Clock

class FakeWeatherAlertsRepository: IWeatherAlertsRepository {

    companion object {
        private val date = Clock.System.now().toString()
        private val affectedZones = listOf<String>("https://api.weather.gov/zones/forecast/PKZ750",
            "https://api.weather.gov/zones/forecast/PKZ777",
            "https://api.weather.gov/zones/forecast/PKZ767",
            "https://api.weather.gov/zones/forecast/PKZ772"
        )

        private val l1 = WeatherAlert("id1","hurricane", date, date, "sender1", null, "Severe","Likely","Expected", affectedZones.subList(0,2), null )
        private val l2 = WeatherAlert("id2","fog", date, date, "Sender Two", null, "Minor","Likely","Expected", affectedZones.subList(2,3),  "instructions blablabla")
        private val l3 = WeatherAlert("id3","heavy snowfall", date, date, "Sender Three", "description3", "Severe", "Likely", "Expected", affectedZones.subList(0,1), null)
        private val l4 = WeatherAlert("id4","tsunami", date, date, "GOV", "description4", "Severe", "Likely", "Expected", affectedZones.subList(0,1), null)
        private val l5 = WeatherAlert("id5","volcano explosion", date, date, "Random sender", "description5", "Severe", "Likely", "Expected", affectedZones.subList(0,1), null)
        private val l6 = WeatherAlert("id6","earthquake", date, date, "sndr", "description6", "Severe", "Likely", "Expected", affectedZones.subList(0,1), null)


        val fakeAlerts = listOf<WeatherAlert>(l1, l2, l3, l4, l5, l6)
    }

    override fun getAlertFromDB(id: String) = flowOf(fakeAlerts.find { it.id == id })

    override fun getAlertsFromDB(): Flow<List<WeatherAlert>> {
        TODO("Not yet implemented")
    }
   

    override suspend fun refreshAlertsFromAPI(): List<WeatherAlert> {
        return listOf(l2, l3)
    }

    override suspend fun getAllAlertsIOS(): List<WeatherAlert> {
        return fakeAlerts
    }

    override suspend fun getZoneInfoFromAPI(zoneCode: String): ZoneNO? {
        TODO("Not yet implemented")
    }
}