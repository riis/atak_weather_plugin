package com.riis.atakweather.weather.gov.data.enum

import com.google.gson.annotations.SerializedName

enum class Certainty(private val value: String) {
    @SerializedName("Actual")
    OBSERVED("Actual"),

    @SerializedName("Likely")
    LIKELY("Likely"),

    @SerializedName("Possible")
    POSSIBLE("Possible"),

    @SerializedName("Unlikely")
    UNLIKELY("Unlikely"),

    @SerializedName("Unknown")
    UNKNOWN("Unknown");

    override fun toString(): String {
        return value
    }
}