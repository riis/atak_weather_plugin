package com.riis.atakweather.weather.gov.data

import com.riis.atakweather.weather.gov.data.schema.Feature

data class AlertResponse(
    val features: List<Feature>
)
