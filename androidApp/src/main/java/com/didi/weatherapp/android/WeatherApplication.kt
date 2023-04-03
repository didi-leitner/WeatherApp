package com.didi.weatherapp.android

import android.app.Application
import com.didi.weatherapp.WeatherAppSDK
import com.didi.weatherapp.android.details.AlertDetailsViewModel
import com.didi.weatherapp.android.feed.WeatherFeedViewModel
import com.didi.weatherapp.repository.WeatherRepo
import com.didi.weatherapp.repository.interfaces.IWeatherAlertsRepository
import com.didi.weatherapp.db.DatabaseDriverFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

class WeatherApplication:  Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
    }

    private fun initKoin(

        dbModule: Module = module {
            single { DatabaseDriverFactory(/*androidContext()*/this@WeatherApplication) }
        },

        dataModule: Module = module {
            single { WeatherAppSDK(get()) }
            single { WeatherRepo(get())  } bind IWeatherAlertsRepository::class
        },

        viewModelsModule: Module = module {
            viewModel { WeatherFeedViewModel(get()) }
            viewModel { AlertDetailsViewModel(get(), get())}

            //viewModel {(id : String) -> AlertDetailsViewModel(id, get())}

            //viewModel { AlertDetailsViewModel(get())}
            //
        },

        ): KoinApplication = startKoin {

        modules(
            dbModule,
            dataModule,
            viewModelsModule,
        )
    }
}

