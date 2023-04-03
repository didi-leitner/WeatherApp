package com.didi.weatherapp.repository

import com.didi.weatherapp.WeatherAppSDK
import com.didi.weatherapp.model.WeatherAlert
import com.didi.weatherapp.network.dto.ZoneNO
import com.didi.weatherapp.repository.interfaces.IWeatherAlertsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WeatherRepo(private val sdk: WeatherAppSDK) : IWeatherAlertsRepository {

    override fun getAlertFromDB(id: String): Flow<WeatherAlert?> {
        return sdk.getAlertFromDB(id)
    }
    override fun getAlertsFromDB(): Flow<List<WeatherAlert>> {
        return  sdk.getAlertsFromDB()/*.map {
            it.map { WeatherAlert(it.id, it.event, it.startDateUTC, it.endDateUTC, it.sender) }
        }*/
    }

    override suspend fun refreshAlertsFromAPI(): List<WeatherAlert> {
        return sdk.refreshLaunchesFromAPI()/*.map { WeatherAlert(it.id, it.event, it.startDateUTC, it.endDateUTC, it.sender) }*/
    }

    override suspend fun getAllAlertsIOS(): List<WeatherAlert> {
        return sdk.getAllLaunchesOld()/*.map { WeatherAlert(it.id, it.event, it.startDateUTC, it.endDateUTC, it.sender) }*/
    }

    override suspend fun getZoneInfoFromAPI(zoneCode: String): ZoneNO? {
        return sdk.getZoneInfoFromAPI(zoneCode)
    }
}