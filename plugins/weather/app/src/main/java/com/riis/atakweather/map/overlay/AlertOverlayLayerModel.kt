package com.riis.atakweather.map.overlay

import com.atakmap.android.hierarchy.action.GoTo
import com.atakmap.android.hierarchy.action.Visibility
import com.atakmap.android.hierarchy.items.AbstractHierarchyListItem2
import com.atakmap.android.hierarchy.items.MapItemUser
import com.atakmap.android.maps.MapItem
import com.atakmap.android.maps.MapView
import com.atakmap.android.menu.MapMenuReceiver
import com.atakmap.android.util.ATAKUtilities

class AlertOverlayLayerModel(private val layer: WeatherLayer) : AbstractHierarchyListItem2(), Visibility, GoTo, MapItemUser {
    override fun getTitle(): String {
        return layer.name
    }

    override fun getDescendantCount(): Int {
        return 0
    }

    override fun getUserObject(): Any {
        return layer
    }

    override fun hideIfEmpty(): Boolean {
        return false
    }

    override fun refreshImpl() {}

    override fun goTo(selected: Boolean): Boolean {
        val mapView = MapView.getMapView()
        ATAKUtilities.scaleToFit(
            mapView,
            layer.metaShape.points,
            mapView.width,
            mapView.height
        )
        if (!selected) return false
        MapMenuReceiver.getMenuWidget()?.let {
            it.openMenuOnItem(layer.metaShape)
            return true
        }
        return false
    }

    override fun getMapItem(): MapItem {
        return layer.metaShape
    }
}