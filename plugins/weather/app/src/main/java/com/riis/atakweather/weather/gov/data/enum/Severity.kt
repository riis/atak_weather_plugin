package com.riis.atakweather.weather.gov.data.enum

import com.google.gson.annotations.SerializedName

enum class Severity(private val value: String) {
    @SerializedName("Extreme")
    EXTREME("Extreme"),

    @SerializedName("Severe")
    SEVERE("Severe"),

    @SerializedName("Moderate")
    MODERATE("Moderate"),

    @SerializedName("Minor")
    MINOR("Minor"),

    @SerializedName("Unknown")
    UNKNOWN("Unknown");

    override fun toString(): String {
        return value
    }
}