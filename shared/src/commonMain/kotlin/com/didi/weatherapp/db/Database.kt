package com.didi.weatherapp.db



import com.didi.AppDatabase
import com.didi.weatherapp.model.WeatherAlert
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries

    internal fun clearDatabase() {
        dbQuery.transaction {
            dbQuery.removeAllAlerts()
        }
    }

    internal fun getWeatherAlerts(): Flow<List<WeatherAlertEntity>> {
        return dbQuery.selectAllAlerts(::mapWAlertSelecting).asFlow().mapToList()//.executeAsList()
    }


    //using this for iOS only, until there's an official solution for flow collection in swift, of better support
    fun getAllAlertsOld(): List<WeatherAlertEntity> {
        return dbQuery.selectAllAlerts(::mapWAlertSelecting).executeAsList()
    }

    private fun mapWAlertSelecting(
        id: String,
        event: String,
        startDateUTC: String,
        endDateUTC: String,
        sender: String
    ): WeatherAlertEntity {

        return WeatherAlertEntity(id, event, startDateUTC, endDateUTC, sender)
    }

    internal fun createWeatherAlert(alerts: List<WeatherAlertEntity>, clear: Boolean) {
        dbQuery.transaction {

            if(clear){
                clearDatabase()
            }

            alerts.forEach { launch ->
                insertWeatherAlert(launch)
            }
        }
    }

    private fun insertWeatherAlert(alert: WeatherAlertEntity) {
        dbQuery.insertAlert(
            id = alert.id,
            event = alert.event,
            startDateUTC = alert.startDateUTC,
            endDateUTC = alert.endDateUTC,
            sender = alert.sender
        )
    }

}