package com.didi.weatherapp.model


data class WeatherAlert (
    val event: String,
    val startDateUTC: String,
    val endDateUTC: String,
    val sender: String,
){

    //var endDateFormated = launchDateUTC.toInstant().toLocalDateTime(TimeZone.UTC)
}



//event name "event", start date "effective", end date "ends", source "senderName" & duration (start date - end date)