package com.riis.atakweather

import android.content.Context
import android.content.Intent
import com.atakmap.android.dropdown.DropDownMapComponent
import com.atakmap.android.ipc.AtakBroadcast.DocumentedIntentFilter
import com.atakmap.android.maps.MapView
import com.atakmap.coremap.log.Log
import com.riis.atakweather.plugin.R

class PluginTemplateMapComponent : DropDownMapComponent() {
    private var pluginContext: Context? = null
    private var ddr: PluginTemplateDropDownReceiver? = null
    override fun onCreate(
        context: Context, intent: Intent,
        view: MapView
    ) {
        context.setTheme(R.style.ATAKPluginTheme)
        super.onCreate(context, intent, view)
        pluginContext = context
        ddr = PluginTemplateDropDownReceiver(
            view, context
        )
        Log.d(TAG, "registering the plugin filter")
        val ddFilter = DocumentedIntentFilter()
        ddFilter.addAction(PluginTemplateDropDownReceiver.SHOW_PLUGIN)
        registerDropDownReceiver(ddr, ddFilter)
    }

    override fun onDestroyImpl(context: Context, view: MapView) {
        super.onDestroyImpl(context, view)
    }

    companion object {
        private const val TAG = "PluginTemplateMapComponent"
    }
}