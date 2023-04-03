package com.didi.weatherapp.model

import kotlin.String

data class WeatherAlert(
  val id: String,
  val event: String,
  val startDateUTC: String,
  val endDateUTC: String,
  val sender: String,
  val desc: String?,
  val severity: String,
  val certainty: String,
  val urgency: String,
  val affectedZones: List<String>,
  val instruction: String?
) {

  val pictureUrl = "https://picsum.photos/seed/$id/900/600"
}




