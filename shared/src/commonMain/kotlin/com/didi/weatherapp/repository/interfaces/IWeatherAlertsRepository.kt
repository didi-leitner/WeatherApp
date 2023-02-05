package com.didi.weatherapp.repository.interfaces

import com.didi.weatherapp.db.WeatherAlertEntity
import com.didi.weatherapp.model.WeatherAlert
import kotlinx.coroutines.flow.Flow

interface IWeatherAlertsRepository {
    fun getAlertsFromDB(): Flow<List<WeatherAlert>>

    suspend fun refreshLaunchesFromAPI(): List<WeatherAlert>
}