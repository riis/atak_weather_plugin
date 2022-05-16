package com.riis.atakweather.map.overlay

import android.widget.BaseAdapter
import com.atakmap.android.hierarchy.HierarchyListFilter
import com.atakmap.android.hierarchy.HierarchyListItem
import com.atakmap.android.maps.DeepMapItemQuery
import com.atakmap.android.maps.DefaultMapGroup
import com.atakmap.android.maps.MapGroup
import com.atakmap.android.overlay.MapOverlay2
import com.riis.atakweather.plugin.R
import com.riis.atakweather.plugin.context.IContextManager

class WeatherOverlay(
    private val contextManager: IContextManager
) : MapOverlay2 {

    private val group: DefaultMapGroup = DefaultMapGroup(name)
    private val overlayModel: WeatherOverlayModel by lazy { WeatherOverlayModel(group) }
    private val query: WeatherOverlayQuery by lazy { WeatherOverlayQuery(overlayModel) }

    companion object {
        private const val TAG = "WeatherOverlay"
    }

    override fun getIdentifier(): String = TAG

    override fun getName(): String = contextManager.getContext().getString(R.string.weather_map_overlay_name)

    override fun getRootGroup(): MapGroup = group

    override fun getQueryFunction(): DeepMapItemQuery = query

    override fun getListModel(
        adapter: BaseAdapter?,
        capabilities: Long,
        filter: HierarchyListFilter?
    ): HierarchyListItem {
        overlayModel.refresh(adapter, filter)
        return overlayModel
    }

    override fun getListModel(
        adapter: BaseAdapter?,
        capabilities: Long,
        sort: HierarchyListItem.Sort?
    ): HierarchyListItem = getListModel(adapter, capabilities, HierarchyListFilter(sort))
}

