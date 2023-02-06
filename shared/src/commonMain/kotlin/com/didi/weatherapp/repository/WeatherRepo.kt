package com.didi.weatherapp.repository

import com.didi.weatherapp.WeatherAppSDK
import com.didi.weatherapp.db.WeatherAlertEntity
import com.didi.weatherapp.repository.interfaces.IWeatherAlertsRepository
import com.didi.weatherapp.model.WeatherAlert
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WeatherRepo(val sdk: WeatherAppSDK) : IWeatherAlertsRepository {
    override fun getAlertsFromDB(): Flow<List<WeatherAlert>> {
        return  sdk.getAlertsFromDB().map {
            it.map { WeatherAlert(it.id, it.event, it.startDateUTC, it.endDateUTC, it.sender) }
        }
    }

    override suspend fun refreshLaunchesFromAPI(): List<WeatherAlert> {
        return sdk.refreshLaunchesFromAPI().map { WeatherAlert(it.id, it.event, it.startDateUTC, it.endDateUTC, it.sender) }
    }

    override suspend fun getAllLaunchesOld(): List<WeatherAlert> {
        return sdk.getAllLaunchesOld().map { WeatherAlert(it.id, it.event, it.startDateUTC, it.endDateUTC, it.sender) }
    }
}