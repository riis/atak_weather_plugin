package com.riis.atakweather.weather.gov.data.enum

import com.google.gson.annotations.SerializedName

enum class Urgency(private val value: String) {
    @SerializedName("Immediate")
    IMMEDIATE("Immediate"),

    @SerializedName("Expected")
    EXPECTED("Expected"),

    @SerializedName("Future")
    FUTURE("Future"),

    @SerializedName("Past")
    PAST("Past"),

    @SerializedName("Unknown")
    UNKNOWN("Unknown"),
    ;

    override fun toString(): String {
        return value
    }
}