package com.riis.atakweather.plugin

import android.content.Context
import android.content.Intent
import com.atakmap.android.dropdown.DropDownMapComponent
import com.atakmap.android.ipc.AtakBroadcast.DocumentedIntentFilter
import com.atakmap.android.maps.MapView
import com.atakmap.coremap.log.Log
import com.riis.atakweather.map.overlay.WeatherOverlay
import com.riis.atakweather.plugin.context.SimpleContextManager

class RIISMapComponent : DropDownMapComponent() {
    private var pluginContext: Context? = null
    private var ddr: RIISDropDownReceiver? = null
    override fun onCreate(
        context: Context, intent: Intent,
        view: MapView
    ) {
        context.setTheme(R.style.ATAKPluginTheme)
        super.onCreate(context, intent, view)
        pluginContext = context
        ddr = RIISDropDownReceiver(
            view, context
        )
        Log.d(TAG, "registering the plugin filter")
        val ddFilter = DocumentedIntentFilter()
        ddFilter.addAction(RIISDropDownReceiver.SHOW_PLUGIN)
        registerDropDownReceiver(ddr, ddFilter)

        val overlay = WeatherOverlay(SimpleContextManager(context))
        view.mapOverlayManager.addOverlay(overlay)
    }

    override fun onDestroyImpl(context: Context, view: MapView) {
        super.onDestroyImpl(context, view)
    }

    companion object {
        private const val TAG = "PluginTemplateMapComponent"
    }
}