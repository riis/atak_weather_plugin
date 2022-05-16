package com.riis.atakweather.plugin

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Button
import com.atak.plugins.impl.PluginLayoutInflater
import com.atakmap.android.cot.CotMapComponent
import com.atakmap.android.dropdown.DropDown.OnStateListener
import com.atakmap.android.dropdown.DropDownReceiver
import com.atakmap.android.hierarchy.HierarchyListReceiver
import com.atakmap.android.ipc.AtakBroadcast
import com.atakmap.android.maps.MapView
import com.atakmap.coremap.cot.event.CotEvent
import com.atakmap.coremap.cot.event.CotPoint
import com.atakmap.coremap.log.Log
import com.atakmap.coremap.maps.time.CoordinatedTime
import com.atakmap.map.layer.opengl.GLLayerFactory
import com.riis.atakweather.map.overlay.GLWeatherLayer
import com.riis.atakweather.map.overlay.WeatherLayer
import com.riis.atakweather.networkKoinModule
import com.riis.atakweather.weather.govWeatherKoinModule
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import timber.log.Timber
import java.util.UUID

class RIISDropDownReceiver(
    mapView: MapView?,
    private val pluginContext: Context
) : DropDownReceiver(mapView), OnStateListener {
    private val mainView: View = PluginLayoutInflater.inflate(
        pluginContext,
        R.layout.main_layout,
        null
    )

    private var layer: WeatherLayer? = null

    //    private val uid = UUID.randomUUID().toString()
    private var lon = -122.084

    /**************************** CONSTRUCTOR  */
    init {
        Timber.d("***** Start dropdown receiver *****")
        startKoin {
            loadKoinModules(listOf(networkKoinModule, govWeatherKoinModule))
        }
        Timber.d("***** Finished init dropdown receiver *****")
    }

    /**************************** PUBLIC METHODS  */
    public override fun disposeImpl() {
        try {
            layer?.let {
                mapView.removeLayer(layer)
            }
        } catch (_: Exception) {
        } finally {
            layer = null
            GLLayerFactory.unregister(GLWeatherLayer.SPI)
        }
    }

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

        GLLayerFactory.register(GLWeatherLayer.SPI)
        val button: Button = mainView.findViewById(R.id.place_marker_button)
        button.setOnClickListener {
            Timber.d("***** Push *****")

            val event = CotEvent()
            val time = CoordinatedTime()

            synchronized(this) {
                if (layer != null) {
                    try {
                        mapView.removeLayer(layer)
                    } catch (_: Exception) {
                    }
                    mapView.addLayer(layer)
                    return@synchronized
                }

                layer = WeatherLayer("RIIS Test Layer").also {
                    mapView.addLayer(MapView.RenderStack.MAP_SURFACE_OVERLAYS, it)
                    it.isVisible = true
                }

                // Refresh Overlay Manager
                AtakBroadcast.getInstance().sendBroadcast(
                    Intent(HierarchyListReceiver.REFRESH_HIERARCHY)
                )
            }


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
        val TAG: String = RIISDropDownReceiver::class.java.simpleName
        const val SHOW_PLUGIN = "com.riis.atakweather.SHOW_PLUGIN"
    }

}