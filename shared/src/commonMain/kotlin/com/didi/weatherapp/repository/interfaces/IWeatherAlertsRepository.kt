package com.didi.weatherapp.repository.interfaces

import com.didi.weatherapp.model.WeatherAlert
import com.didi.weatherapp.network.dto.ZoneNO
import kotlinx.coroutines.flow.Flow

interface IWeatherAlertsRepository {

    @Throws(Exception::class)
    fun getAlertFromDB(id: String): Flow<WeatherAlert?>

    @Throws(Exception::class)
    fun getAlertsFromDB(): Flow<List<WeatherAlert>>

    @Throws(Exception::class)
    suspend fun refreshAlertsFromAPI(): List<WeatherAlert>

    @Throws(Exception::class)
    suspend fun getAllAlertsIOS(): List<WeatherAlert>

    @Throws(Exception::class)
    suspend fun getZoneInfoFromAPI(zoneCode: String): ZoneNO?

}