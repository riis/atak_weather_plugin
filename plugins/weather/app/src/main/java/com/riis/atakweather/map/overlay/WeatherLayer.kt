package com.riis.atakweather.map.overlay

import com.atakmap.android.maps.MetaShape
import com.atakmap.coremap.maps.coords.GeoBounds
import com.atakmap.coremap.maps.coords.GeoPoint
import com.atakmap.coremap.maps.coords.GeoPointMetaData
import com.atakmap.coremap.maps.coords.MutableGeoBounds
import com.atakmap.map.layer.AbstractLayer
import java.util.UUID

class WeatherLayer(name: String) : AbstractLayer(name) {

    val metaShape = object : MetaShape(UUID.randomUUID().toString()) {
        init {
            val TAG = "WeatherLayer"
            setMetaString("callsign", TAG)
            setMetaString("shapeName", TAG)
            type = "weather_layer"
        }

        override fun getPoints(): Array<GeoPoint> {
            return arrayOf(
                GeoPoint(32.421998, -122.084),
                GeoPoint(32.421998, -120.084),
                GeoPoint(33.421998, -120.084),
                GeoPoint(33.421998, -122.084),
                GeoPoint(32.921998, -124.084),
            )
        }

        override fun getMetaDataPoints(): Array<GeoPointMetaData> {
            return GeoPointMetaData.wrap(points)
        }

        override fun getBounds(p0: MutableGeoBounds?): GeoBounds {
            return GeoBounds.createFromPoints(points)
        }
    }

    fun getBounds(): GeoBounds {
        return GeoBounds.createFromPoints(metaShape.points)
    }
}