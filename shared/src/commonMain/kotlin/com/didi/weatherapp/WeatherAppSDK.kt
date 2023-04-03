package com.didi.weatherapp


import com.didi.weatherapp.db.Database
import com.didi.weatherapp.db.DatabaseDriverFactory
import com.didi.weatherapp.model.WeatherAlert
import com.didi.weatherapp.network.WeatherApi
import com.didi.weatherapp.network.dto.ZoneNO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking


class WeatherAppSDK (databaseDriverFactory: DatabaseDriverFactory) {
    private val database = Database(databaseDriverFactory)
    private val api = WeatherApi()

    @Throws(Exception::class) fun getAlertFromDB(id: String): Flow<WeatherAlert?> {
        return database.getWeatherAlert(id)
    }
    @Throws(Exception::class) fun getAlertsFromDB(): Flow<List<WeatherAlert>> {
        return database.getWeatherAlerts()
    }

    @Throws(Exception::class) suspend fun refreshLaunchesFromAPI(): List<WeatherAlert> {

        try {
            return api.getWeatherAlerts().also {
                database.createWeatherAlert(it, clear = true)
            }
        }catch (ex: Exception){
            ex.printStackTrace()
        }

        return emptyList()


    }

    @Throws(Exception::class) suspend fun getZoneInfoFromAPI(zoneCode: String): ZoneNO? {

        try {
            return api.getZoneInfo(zoneCode)
        }catch (ex: Exception){
            ex.printStackTrace()
        }

        return null


    }

    //old method
    @Throws(Exception::class) suspend fun getAllLaunchesOld(): List<WeatherAlert> {

        val cachedLaunches = database.getAllAlertsOld()
        if (cachedLaunches.isNotEmpty()) {
            return cachedLaunches
        } else {

            try {

                return runBlocking (Dispatchers.Default){

                    api.getWeatherAlerts().also {

                        //database.clearDatabase()
                        database.createWeatherAlert(it, true)
                    }

                }



            }catch (e: Exception){
                e.printStackTrace()
            }

        }

        return emptyList()
    }




}
