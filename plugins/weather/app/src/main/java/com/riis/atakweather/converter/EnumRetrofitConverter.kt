package com.riis.atakweather.converter

import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type


class EnumRetrofitConverter : Converter.Factory() {
    override fun stringConverter(type: Type, annotations: Array<out Annotation>, retrofit: Retrofit): Converter<*, String>? {
        return if (type is Class<*> && type.isEnum) {
            Converter<Enum<*>, String> { value -> value.toString() }
        } else {
            null
        }
    }
}