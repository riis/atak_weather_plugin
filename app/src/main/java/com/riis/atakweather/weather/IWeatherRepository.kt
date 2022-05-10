package com.riis.atakweather.weather

interface IWeatherRepository {
    fun queryWeatherAlerts(
        successCallback: (List<WeatherAlert>) -> Unit,
        failureCallback: (Throwable) -> Unit
    )
}