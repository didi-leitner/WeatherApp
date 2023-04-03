package com.didi.weatherapp.model

import com.didi.weatherapp.network.dto.ZoneNO

data class WeatherAlertDetail(
    val id: String,
    val startDateUTC: String,
    val endDateUTC: String,
    val sender: String,
    val desc: String?,
    val severity: String,
    val certainty: String,
    val urgency: String,
    val affectedZones: List<Zone>,
    val instruction: String?
) {

    val pictureUrl = "https://picsum.photos/seed/$id/900/600"
}