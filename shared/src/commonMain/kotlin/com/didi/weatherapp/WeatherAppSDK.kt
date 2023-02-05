package com.didi.weatherapp


import com.didi.weatherapp.db.Database
import com.didi.weatherapp.db.DatabaseDriverFactory
import com.didi.weatherapp.db.WeatherAlertEntity
import com.didi.weatherapp.model.WeatherAlert
import com.didi.weatherapp.network.WeatherApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking


class WeatherAppSDK (databaseDriverFactory: DatabaseDriverFactory) {
    private val database = Database(databaseDriverFactory)
    private val api = WeatherApi()

    @Throws(Exception::class) fun getAlertsFromDB(): Flow<List<WeatherAlertEntity>> {
        //TODO format in repo //var endDateFormated = launchDateUTC.toInstant().toLocalDateTime(TimeZone.UTC)
        return database.getWeatherAlerts()

    }

    @Throws(Exception::class) suspend fun refreshLaunchesFromAPI(): List<WeatherAlertEntity> {

        try {
            return api.getWeatherAlerts().also {
                database.createWeatherAlert(it, clear = true)
            }
        }catch (ex: Exception){
            ex.printStackTrace()
        }

        return emptyList()


    }

    //old method
    suspend fun getAllLaunchesOld(): List<WeatherAlertEntity> {
        //return database.getAllLaunchesOld()

        println("TESTT111")
        val cachedLaunches = database.getAllAlertsOld()

        println("TESTTT222")
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
