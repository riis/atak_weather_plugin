package com.riis.atakweather.map.overlay

import android.view.View
import android.widget.Button
import android.widget.Toast
import com.atakmap.android.hierarchy.action.Search
import com.atakmap.android.hierarchy.action.Visibility2
import com.atakmap.android.hierarchy.items.AbstractHierarchyListItem2
import com.atakmap.android.maps.MapGroup
import com.atakmap.android.maps.MapView

class WeatherOverlayModel(private val group: MapGroup) : AbstractHierarchyListItem2(), Search, Visibility2, View.OnClickListener {

    init {
        asyncRefresh = true
    }

    override fun getTitle(): String = "Weather Overlay Model"

    override fun getDescendantCount(): Int = 0

    override fun getUserObject(): Any = this

    override fun hideIfEmpty(): Boolean = true

    override fun refreshImpl() {
        val filtered = getLayers()
            .map { AlertOverlayLayerModel(it) }
            .filter { filter.accept(it) }

        sortItems(filtered)
        updateChildren(filtered)
    }

    fun getLayers(): List<WeatherLayer> {
        return MapView.getMapView()
            .getLayers(MapView.RenderStack.MAP_SURFACE_OVERLAYS)
            .filterIsInstance<WeatherLayer>()
            .map {
                val shape = it.metaShape
                if (shape.group == null) group.addItem(shape)
                it
            }
    }

    override fun onClick(view: View?) {
        if (view is Button) Toast.makeText(
            MapView.getMapView().context,
            view.text,
            Toast.LENGTH_LONG
        ).show()
    }
}