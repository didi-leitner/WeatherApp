package com.didi.weatherapp.android.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.didi.weatherapp.WeatherAppSDK
import com.didi.weatherapp.android.repository.interfaces.IWeatherAlertsRepository
import com.didi.weatherapp.model.WeatherAlert
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WeatherRepo(val sdk: WeatherAppSDK) : IWeatherAlertsRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun getAlertsFromDB(): Flow<List<WeatherAlert>> {
        return  sdk.getAlertsFromDB().map {
        //TODO formatt here
            it.map { WeatherAlert(it.id, it.event, it.startDateUTC, it.endDateUTC, it.sender) }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun refreshLaunchesFromAPI(): List<WeatherAlert> {
        return sdk.refreshLaunchesFromAPI().map { WeatherAlert(it.id, it.event, it.startDateUTC, it.endDateUTC, it.sender) }
    }
}