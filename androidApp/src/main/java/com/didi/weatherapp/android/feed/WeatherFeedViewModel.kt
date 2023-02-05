package com.didi.weatherapp.android.feed

import androidx.lifecycle.*
import com.didi.weatherapp.repository.interfaces.IWeatherAlertsRepository
import com.didi.weatherapp.model.WeatherAlert
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

//@HiltViewModel
class WeatherFeedViewModel (private val repoAlerts: IWeatherAlertsRepository): ViewModel() {

    val isRefreshing = MutableStateFlow<Boolean>(false)

    //DB, unique source of truth
    val wAlers: StateFlow<List<WeatherAlert>>  = repoAlerts.getAlertsFromDB()
        .stateIn(scope = viewModelScope,
            started = WhileSubscribed(5_000),
            initialValue = emptyList(),
        )

    init {
        println("TESTT ROCKET iNiT VIEWMODEL")
        refresh()


    }

    fun refresh() {
        viewModelScope.launch(Dispatchers.IO) {
            isRefreshing.value = true
            //delay(5000)
            repoAlerts.refreshLaunchesFromAPI() //will update DB, which is observed
            isRefreshing.value = false
        }
    }

}