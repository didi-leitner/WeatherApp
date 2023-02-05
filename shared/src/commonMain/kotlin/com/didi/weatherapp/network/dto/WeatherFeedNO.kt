package com.didi.weatherapp.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class WeatherFeedNO (

    @SerialName("features")
    val features: List<Feature>

)

@Serializable
class Feature(

    @SerialName("properties")
    val properties: Properties

)

@Serializable
class Properties(

    @SerialName("id")
    val id: String,

    @SerialName("event")
    val event: String,

    @SerialName("description")
    val desc: String,

    @SerialName("sender")
    val sender: String,

    @SerialName("effective")
    val startDate: String,

    @SerialName("expires")
    val endDate: String
)

