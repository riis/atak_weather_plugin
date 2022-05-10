package com.riis.atakweather

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Button
import com.atak.plugins.impl.PluginLayoutInflater
import com.atakmap.android.cot.CotMapComponent
import com.atakmap.android.dropdown.DropDown.OnStateListener
import com.atakmap.android.dropdown.DropDownReceiver
import com.atakmap.android.ipc.AtakBroadcast
import com.atakmap.android.maps.MapView
import com.atakmap.coremap.cot.event.CotEvent
import com.atakmap.coremap.cot.event.CotPoint
import com.atakmap.coremap.log.Log
import com.atakmap.coremap.maps.time.CoordinatedTime
import com.riis.atakweather.plugin.R
import com.riis.atakweather.weather.govWeatherKoinModule
import org.koin.core.context.loadKoinModules
import timber.log.Timber
import java.util.UUID

class PluginTemplateDropDownReceiver(
    mapView: MapView?,
    private val pluginContext: Context
) : DropDownReceiver(mapView), OnStateListener {
    private val mainView: View = PluginLayoutInflater.inflate(
        pluginContext,
        R.layout.main_layout,
        null
    )

    //    private val uid = UUID.randomUUID().toString()
    private var lon = -122.084

    /**************************** CONSTRUCTOR  */
    init {
        loadKoinModules(listOf(networkKoinModule, govWeatherKoinModule))
    }

    /**************************** PUBLIC METHODS  */
    public override fun disposeImpl() {}

    /**************************** INHERITED METHODS  */
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action ?: return
        if (action == SHOW_PLUGIN) {
            Log.d(TAG, "showing plugin drop down")
            showDropDown(
                mainView,
                HALF_WIDTH,
                FULL_HEIGHT,
                FULL_WIDTH,
                HALF_HEIGHT,
                false,
                this
            )
        }

        val button: Button = mainView.findViewById(R.id.place_marker_button)
        button.setOnClickListener {
            Timber.d("***** Push *****")

            val event = CotEvent()
            val time = CoordinatedTime()

            event.uid = UUID.randomUUID().toString()
            event.type = "a-f-G-U-C-I"
            event.time = time
            event.start = time
            event.stale = time.addMinutes(10) // point lasts 10 min
            event.how = "h-e"
            event.setPoint(
                CotPoint(
                    32.421998,
                    lon,
                    0.0, // height above ellipsoid
                    2.0, // circular error in meters
                    2.0 // linear error in meters
                )
            )
            lon += 1.0

            CotMapComponent.getInternalDispatcher().dispatch(event)
            AtakBroadcast.getInstance()
        }
    }

    override fun onDropDownSelectionRemoved() {}
    override fun onDropDownVisible(v: Boolean) {}
    override fun onDropDownSizeChanged(width: Double, height: Double) {}
    override fun onDropDownClose() {}

    companion object {
        val TAG: String = PluginTemplateDropDownReceiver::class.java.simpleName
        const val SHOW_PLUGIN = "com.riis.atakweather.SHOW_PLUGIN"
    }

}