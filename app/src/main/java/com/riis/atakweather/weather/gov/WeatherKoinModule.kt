package com.riis.atakweather.weather

import com.google.gson.GsonBuilder
import com.riis.atakweather.weather.gov.WeatherService
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val govWeatherKoinModule = module {
    val timestampConverter = GsonBuilder().setDateFormat("yyyy-MM-dd'T'hh:mm:ss'T'ZD").create()

    single(named("weatherRetrofitGateway")) {
        Retrofit.Builder()
            .baseUrl("https://api.weather.gov")
            .addConverterFactory(GsonConverterFactory.create(timestampConverter))
            .client(get(named("genericOkHttpClient")))
            .build()
    }

    single {
        get<Retrofit>(named("weatherRetrofitGateway"))
            .create(WeatherService::class.java)
    }
}