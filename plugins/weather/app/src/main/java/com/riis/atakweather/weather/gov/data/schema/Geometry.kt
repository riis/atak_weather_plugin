package com.riis.atakweather.weather.gov.data.schema

data class Geometry(
    val areas: List<Shape>
)

typealias Shape = List<Coordinate>

typealias Coordinate = List<Double>

internal fun Coordinate.getLatitude() = this[0]
internal fun Coordinate.getLongitude() = this[1]