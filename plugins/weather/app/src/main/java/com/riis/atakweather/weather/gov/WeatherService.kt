package com.riis.atakweather.weather.gov

import com.riis.atakweather.weather.gov.data.AlertResponse
import com.riis.atakweather.weather.gov.data.enum.Severity
import com.riis.atakweather.weather.gov.data.enum.Status
import com.riis.atakweather.weather.gov.data.enum.Urgency
import com.riis.atakweather.weather.gov.data.enum.Certainty
import com.riis.atakweather.weather.gov.data.enum.MessageType
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface WeatherService {
    @JvmSuppressWildcards
    @GET("/alerts")
    fun getAlerts(
        @Query("start") start: String? = null,
        @Query("end") end: String? = null,
        @Query("status") status: Status? = null,
        @Query("message_type") messageType: MessageType? = null,
        @Query("urgency[]") urgency: List<Urgency> = listOf(),
        @Query("severity[]") severity: List<Severity> = listOf(),
        @Query("certainty[]") certainty: List<Certainty> = listOf(),
        @Query("limit") limit: Int? = null
    ): Call<AlertResponse>

    @GET
    fun getAlerts(
        @Url url: String
    ): Call<AlertResponse>
}