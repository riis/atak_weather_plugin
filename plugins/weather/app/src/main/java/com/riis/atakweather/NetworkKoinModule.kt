package com.riis.atakweather

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import timber.log.Timber
import java.util.concurrent.TimeUnit

val networkKoinModule = module {
    single {
        HttpLoggingInterceptor { message ->
            Timber.tag("OkHttp").d(message)
        }.setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    single(named("genericOkHttpClient")) {
        OkHttpClient().newBuilder()
            .readTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }
}