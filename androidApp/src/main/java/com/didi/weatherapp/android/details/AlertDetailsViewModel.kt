package com.didi.weatherapp.android.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.didi.weatherapp.model.WeatherAlert
import com.didi.weatherapp.repository.interfaces.IWeatherAlertsRepository
import kotlinx.coroutines.flow.*


class AlertDetailsViewModel  (id: String, repoAlerts: IWeatherAlertsRepository): ViewModel() {

    //must not be a function, otherwise .collectAsState will cause infinite/multiple recompositions
    val wAlert: StateFlow<WeatherAlert?>  = repoAlerts.getAlertFromDB(id).map {
        it?.let {
            for(az in it.affectedZones){
                val li = az.lastIndexOf("/")
                val zoneCode = az.substring(li+1)
                val zone = repoAlerts.getZoneInfoFromAPI(zoneCode)
                println("TESTT zone " + zone?.properties?.radarStation)
            }
        }

        it
    }.stateIn(scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null,
        )

}