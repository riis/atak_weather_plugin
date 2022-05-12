package com.riis.atakweather.weather

import com.riis.atakweather.map.shape.IShape

data class WeatherAlert(
    val title: String,
    val description: String,
    val area: List<IShape>
)
