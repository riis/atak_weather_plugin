package com.riis.atakweather.weather.gov.data.enum

import com.google.gson.annotations.SerializedName

enum class Status(private val value: String) {
    @SerializedName("actual")
    ACTUAL("actual"),

    @SerializedName("exercise")
    EXERCISE("exercise"),

    @SerializedName("system")
    SYSTEM("system"),

    @SerializedName("test")
    TEST("test"),

    @SerializedName("draft")
    DRAFT("draft"),
    ;

    override fun toString(): String {
        return value
    }
}