package com.didi.weatherapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform