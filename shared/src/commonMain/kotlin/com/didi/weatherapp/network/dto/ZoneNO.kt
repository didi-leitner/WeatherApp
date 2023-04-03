package com.didi.weatherapp.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable



@Serializable
class ZoneNO(

    @SerialName("properties")
    val properties: ZoneProperties

)

@Serializable
class ZoneProperties(

    @SerialName("radarStation")
    val radarStation: String?,

)