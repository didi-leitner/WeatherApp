package com.didi.weatherapp.android.repository.interfaces

import com.didi.weatherapp.db.WeatherAlertEntity
import kotlinx.coroutines.flow.Flow

interface IWeatherAlertsRepository {
    fun getAlertsFromDB(): Flow<List<WeatherAlertEntity>>

    suspend fun getLaunchesFromAPI(): List<WeatherAlertEntity>
}