package com.didi.weatherapp.repository


import com.didi.weatherapp.db.Database
import com.didi.weatherapp.db.DatabaseDriverFactory
import com.didi.weatherapp.model.WeatherAlert
import com.didi.weatherapp.network.WeatherApi
import com.didi.weatherapp.network.dto.ZoneNO
import com.didi.weatherapp.repository.interfaces.IWeatherAlertsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking


class WeatherRepo (databaseDriverFactory: DatabaseDriverFactory): IWeatherAlertsRepository {
    private val database = Database(databaseDriverFactory)
    private val api = WeatherApi()

    @Throws(Exception::class)
    override fun getAlertFromDB(id: String): Flow<WeatherAlert?> {
        return database.getWeatherAlert(id)
    }
    @Throws(Exception::class)
    override fun getAlertsFromDB(): Flow<List<WeatherAlert>> {
        return database.getWeatherAlerts()
    }

    @Throws(Exception::class)
    override suspend fun refreshAlertsFromAPI(): List<WeatherAlert> {

        try {
            return api.getWeatherAlerts().also {
                database.createWeatherAlert(it, clear = true)
            }
        }catch (ex: Exception){
            ex.printStackTrace()
        }

        return emptyList()


    }

    @Throws(Exception::class)
    override suspend fun getZoneInfoFromAPI(zoneCode: String): ZoneNO? {

        try {
            return api.getZoneInfo(zoneCode)
        }catch (ex: Exception){
            ex.printStackTrace()
        }

        return null


    }

    //old method
    @Throws(Exception::class)
    override suspend fun getAllAlertsIOS(): List<WeatherAlert> {

        val cachedLaunches = database.getAllAlertsIOS()
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
