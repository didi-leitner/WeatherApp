package com.didi.weatherapp.repository.interfaces

import com.didi.weatherapp.model.WeatherAlert
import com.didi.weatherapp.network.dto.ZoneNO
import kotlinx.coroutines.flow.Flow

interface IWeatherAlertsRepository {

    fun getAlertFromDB(id: String): Flow<WeatherAlert?>

    fun getAlertsFromDB(): Flow<List<WeatherAlert>>

    suspend fun refreshAlertsFromAPI(): List<WeatherAlert>

    suspend fun getAllAlertsIOS(): List<WeatherAlert>

    suspend fun getZoneInfoFromAPI(zoneCode: String): ZoneNO?

}