package com.riis.atakweather.weather.gov.data.enum

enum class MessageType(private val value: String) {
    ALERT("alert"),
    UPDATE("update"),
    CANCEL("cancel"),
    ;

    override fun toString(): String {
        return value
    }
}