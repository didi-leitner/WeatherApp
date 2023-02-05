package com.didi.weatherapp.model


data class WeatherAlert (
    val id: String,
    val event: String,
    val startDateUTC: String,
    val endDateUTC: String,
    val sender: String,
){

}



//event name "event", start date "effective", end date "ends", source "senderName" & duration (start date - end date)