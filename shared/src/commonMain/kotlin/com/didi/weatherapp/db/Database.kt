package com.didi.weatherapp.db



import com.didi.AppDatabase
import com.didi.weatherapp.model.WeatherAlert
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import kotlinx.coroutines.flow.Flow

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries

    private fun clearDatabase() {
        dbQuery.transaction {
            dbQuery.removeAllAlerts()
        }
    }

    internal fun getWeatherAlerts(): Flow<List<WeatherAlert>> {
        return dbQuery.selectAllAlerts(::mapWAlertSelecting).asFlow().mapToList()//.executeAsList()
    }

    internal fun getWeatherAlert(id: String): Flow<WeatherAlert?> {
        return dbQuery.selectAlert(id, ::mapWAlertSelecting).asFlow().mapToOne()
    }


    //using this for iOS only, until there's an official solution for flow collection in swift, of better support
    fun getAllAlertsIOS(): List<WeatherAlert> {
        return dbQuery.selectAllAlerts(::mapWAlertSelecting).executeAsList()
    }

    private fun mapWAlertSelecting(
        id: String,
        event: String,
        startDateUTC: String,
        endDateUTC: String,
        sender: String,
        desc: String?,
        severity: String,
        certainty: String,
        urgency: String,
        affecterdZonesStr: String,
        instruction: String?,

        ): WeatherAlert {

        val affecterdZones = affecterdZonesStr.split(";")
        return WeatherAlert(id, event, startDateUTC, endDateUTC, sender, desc, severity, certainty, urgency,affecterdZones, instruction)
    }

    internal fun createWeatherAlert(alerts: List<WeatherAlert>, clear: Boolean) {
        dbQuery.transaction {

            if(clear){
                clearDatabase()
            }

            alerts.forEach { launch ->
                insertWeatherAlert(launch)
            }
        }
    }

    private fun insertWeatherAlert(alert: WeatherAlert) {

        var affecterdZonesStr = ""
        alert.affectedZones.forEach {
            affecterdZonesStr += "$it;"
        }
        affecterdZonesStr.removeSuffix(";")
        dbQuery.insertAlert(
            id = alert.id,
            event = alert.event,
            startDateUTC = alert.startDateUTC,
            endDateUTC = alert.endDateUTC,
            sender = alert.sender,
            desc = alert.desc,
            severity = alert.severity,
            certainty = alert.certainty,
            urgency = alert.urgency,
            affectedZones = affecterdZonesStr,
            instruction =  alert.instruction,
        )
    }

}