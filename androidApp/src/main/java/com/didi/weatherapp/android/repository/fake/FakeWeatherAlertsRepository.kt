package com.didi.weatherapp.android.repository.fake


import android.os.Build
import androidx.annotation.RequiresApi
import com.didi.weatherapp.android.repository.interfaces.IWeatherAlertsRepository
import com.didi.weatherapp.model.WeatherAlert
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*
import java.time.Instant

@RequiresApi(Build.VERSION_CODES.O)
class FakeWeatherAlertsRepository: IWeatherAlertsRepository {

    companion object {
        private val date = Instant.now().toString()

        private val l1 = WeatherAlert("id1","hurricane",date, date, "sender1")
        private val l2 = WeatherAlert("id2","fog",date, date, "Sender Two")
        private val l3 = WeatherAlert("id3","heavy snowfall",date, date, "Sender Three")
        private val l4 = WeatherAlert("id4","tsunami",date, date, "GOV")
        private val l5 = WeatherAlert("id5","volcano explosion", date, date, "Random sender")
        private val l6 = WeatherAlert("id6","earthquake", date, date, "sndr")


        val fakeAlerts = listOf<WeatherAlert>(l1, l2, l3, l4, l5, l6)
    }

    override fun getAlertsFromDB(): Flow<List<WeatherAlert>> {
        TODO("Not yet implemented")
    }
   

    override suspend fun refreshLaunchesFromAPI(): List<WeatherAlert> {
        return listOf(l2,l3)
    }
}