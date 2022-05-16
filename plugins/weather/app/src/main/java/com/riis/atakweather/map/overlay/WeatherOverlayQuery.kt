package com.riis.atakweather.map.overlay

import com.atakmap.android.maps.DeepMapItemQuery
import com.atakmap.android.maps.MapItem
import com.atakmap.android.maps.MapView
import com.atakmap.coremap.maps.coords.GeoBounds
import com.atakmap.coremap.maps.coords.GeoPoint
import java.util.SortedSet
import java.util.TreeSet

class WeatherOverlayQuery(private val model: WeatherOverlayModel) : DeepMapItemQuery {
    override fun deepFindItem(p0: MutableMap<String, String>?): MapItem? {
        return null
    }

    override fun deepFindItems(p0: MutableMap<String, String>?): MutableList<MapItem>? {
        return null
    }

    override fun deepFindItems(p0: GeoPoint?, p1: Double, p2: MutableMap<String, String>?): MutableCollection<MapItem>? {
        return null
    }

    override fun deepFindItems(bounds: GeoBounds?, metadata: MutableMap<String, String>?): MutableCollection<MapItem> {
        val ret: SortedSet<MapItem> = TreeSet(
            MapItem.ZORDER_HITTEST_COMPARATOR
        )
        model.getLayers().forEach {
            if (it.isVisible && it.getBounds().intersects(bounds)) {
                ret.add(it.metaShape)
            }
        }
        return ret
    }

    override fun deepFindClosestItem(p0: GeoPoint?, p1: Double, p2: MutableMap<String, String>?): MapItem? {
        return null
    }

    @Deprecated("Deprecated")
    override fun deepHitTest(
        xpos: Int,
        ypos: Int,
        point: GeoPoint?,
        view: MapView?
    ): MapItem? {
        model.getLayers().forEach {
            if (it.isVisible && it.getBounds().contains(point)) return it.metaShape
        }
        return null
    }

    @Deprecated("Deprecated")
    override fun deepHitTestItems(
        xpos: Int,
        ypos: Int,
        point: GeoPoint?,
        view: MapView?
    ): SortedSet<MapItem> {
        val ret: SortedSet<MapItem> = TreeSet(
            MapItem.ZORDER_HITTEST_COMPARATOR
        )
        model.getLayers().forEach {
            if (it.isVisible && it.getBounds().contains(point)) ret.add(it.metaShape)
        }
        return ret
    }
}