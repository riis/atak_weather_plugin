package com.riis.atakweather.weather.gov

import com.riis.atakweather.map.shape.Coordinate
import com.riis.atakweather.map.shape.Polygon
import com.riis.atakweather.weather.IWeatherRepository
import com.riis.atakweather.weather.WeatherAlert
import com.riis.atakweather.weather.gov.data.AlertResponse
import com.riis.atakweather.weather.gov.data.schema.getLatitude
import com.riis.atakweather.weather.gov.data.schema.getLongitude
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

// Repository for obtaining weather data from https://www.weather.gov/documentation/services-web-api
class GovWeatherRepository(private val weatherService: WeatherService) : IWeatherRepository {
    override fun queryWeatherAlerts(
        successCallback: (List<WeatherAlert>) -> Unit,
        failureCallback: (Throwable) -> Unit
    ) {
        weatherService.getAlerts().enqueue(object : Callback<AlertResponse> {
            override fun onResponse(call: Call<AlertResponse>, response: Response<AlertResponse>) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    successCallback(responseToAlert(body))
                } else {
                    failureCallback(Exception(response.message()))
                }
            }

            override fun onFailure(call: Call<AlertResponse>, error: Throwable) {
                failureCallback(error)
            }
        })
    }

    private fun responseToAlert(response: AlertResponse): List<WeatherAlert> {
        return response.features.map { alert ->
            WeatherAlert(
                title = alert.properties.headline,
                description = alert.properties.description,
                area = alert.geometry.areas.map { shape ->
                    Polygon(shape.map { Coordinate(it.getLatitude(), it.getLongitude()) })
                }
            )
        }
    }
}