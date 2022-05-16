package com.riis.atakweather.test

import com.riis.atakweather.networkKoinModule
import com.riis.atakweather.weather.gov.WeatherService
import com.riis.atakweather.weather.gov.data.enum.MessageType
import com.riis.atakweather.weather.gov.data.enum.Severity
import com.riis.atakweather.weather.gov.data.enum.Status
import com.riis.atakweather.weather.gov.data.enum.Urgency
import com.riis.atakweather.weather.govWeatherKoinModule
import org.junit.Test
import org.koin.core.component.get
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import kotlin.test.assertTrue

class WeatherAPITest : KoinTest {
    @Test
    fun `can get alert`() {
        startKoin {
            modules(networkKoinModule, govWeatherKoinModule)
            val weatherService = get<WeatherService>()
            val response = weatherService.getAlerts(
                messageType = MessageType.ALERT,
                status = Status.ACTUAL,
                urgency = listOf(Urgency.IMMEDIATE),
                severity = listOf(Severity.EXTREME,  Severity.MODERATE),
                limit = 1
            ).execute()

            assertTrue(response.isSuccessful)
            println(response.body())
        }
    }
}