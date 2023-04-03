package com.didi.weatherapp.android.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.didi.weatherapp.model.WeatherAlert
import com.didi.weatherapp.model.WeatherAlertDetail
import com.didi.weatherapp.model.Zone
import com.didi.weatherapp.repository.interfaces.IWeatherAlertsRepository
import kotlinx.coroutines.flow.*


class AlertDetailsViewModel  (id: String, repoAlerts: IWeatherAlertsRepository): ViewModel() {

    //must not be a function, otherwise .collectAsState will cause infinite/multiple recompositions
    val wAlert: StateFlow<WeatherAlertDetail?>  = repoAlerts.getAlertFromDB(id).map {
        if(it != null){
            val zones = mutableListOf<Zone>()
            for(az in it.affectedZones){
                val li = az.lastIndexOf("/")
                val zoneCode = az.substring(li+1)
                val zone = repoAlerts.getZoneInfoFromAPI(zoneCode)
                println("TESTT zone " + zone?.properties?.radarStation)
                zones.add(Zone(az, (zone?.properties?.radarStation != null)))
            }
            WeatherAlertDetail(it.id, it.startDateUTC, it.endDateUTC, it.sender, it.desc,
                it.severity, it.certainty, it.urgency, zones, it.instruction)
        } else null


    }.stateIn(scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null,
        )

}