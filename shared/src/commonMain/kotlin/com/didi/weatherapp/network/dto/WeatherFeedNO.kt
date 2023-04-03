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
    val desc: String?,

    @SerialName("sender")
    val sender: String,

    @SerialName("effective")
    val startDate: String,

    @SerialName("expires")
    val endDate: String,

    @SerialName("severity")
    val severity: String,

    @SerialName("certainty")
    val certainty: String,

    @SerialName("urgency")
    val urgency: String,

    @SerialName("affectedZones")
    val affectedZones: List<String>,

    @SerialName("instruction")
    val instruction: String?,

)


//event name "event", start date "effective", end date "ends", source "senderName" & duration (start date - end date)


/*the same image of the alert -> If you long tap on the image you can drag it wherever you want on the screen
the period (the start date - the end date)
the severity
the certainty
the urgency
the source
the description
the names of the affected zones (& whether or not they are a “radarStation”).
You can see ids/URLs of the affected zones under the “ affectedZones” node
the instructions (BONUS: make the instructions and description text to contain only 2 lines and expand if you click on the text itself)
*/

