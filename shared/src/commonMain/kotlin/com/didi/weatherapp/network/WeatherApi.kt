package com.didi.weatherapp.network

import com.didi.weatherapp.db.WeatherAlertEntity
import com.didi.weatherapp.model.WeatherAlert
import com.didi.weatherapp.network.dto.WeatherFeedNO
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement

class WeatherApi {

    companion object {
        const val BASE_URL = "https://api.weather.gov/"
    }

    private val mJson = Json {
        ignoreUnknownKeys = true
        useAlternativeNames = false
    }
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(mJson)
        }

        install(HttpTimeout) {
            //doesn't seem to work
            //https://ktor.io/docs/timeout.html#limitations
            requestTimeoutMillis = 3000
        }

    }

    @Throws (Exception::class)
    suspend fun getWeatherAlerts(): List<WeatherAlertEntity> {
        val response= httpClient.get(BASE_URL + "alerts/active?status=actual&message_type=alert")

        val networkObj = response.body<WeatherFeedNO>()

        return networkObj.features.map {
            WeatherAlertEntity(it.properties.id, it.properties.event, it.properties.startDate,
                it.properties.endDate, it.properties.sender)
        }

    }
}
