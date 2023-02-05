package com.didi.weatherapp.android.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.didi.weatherapp.WeatherAppSDK
import com.didi.weatherapp.android.repository.interfaces.IWeatherAlertsRepository
import com.didi.weatherapp.db.WeatherAlertEntity
import com.didi.weatherapp.model.WeatherAlert
import kotlinx.coroutines.flow.Flow

class WeatherRepo(val sdk: WeatherAppSDK) : IWeatherAlertsRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun getAlertsFromDB(): Flow<List<WeatherAlertEntity>> {
        return  sdk.getAlertsFromDB()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getLaunchesFromAPI(): List<WeatherAlertEntity> {
        return sdk.refreshLaunchesFromAPI()
    }
}