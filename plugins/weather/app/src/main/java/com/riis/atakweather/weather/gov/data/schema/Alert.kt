package com.riis.atakweather.weather.gov.data.schema

import com.riis.atakweather.weather.gov.data.enum.Certainty
import com.riis.atakweather.weather.gov.data.enum.MessageType
import com.riis.atakweather.weather.gov.data.enum.Severity
import com.riis.atakweather.weather.gov.data.enum.Status
import com.riis.atakweather.weather.gov.data.enum.Urgency

data class Alert(
    val id: String,
    val areaDesc: String,
    val status: Status,
    val messageType: MessageType,
    val severity: Severity,
    val certainty: Certainty,
    val urgency: Urgency,
    val headline: String,
    val description: String,
    val instruction: String,
)