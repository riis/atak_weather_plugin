package com.riis.atakweather.map.overlay

import android.graphics.Bitmap
import android.graphics.Color
import android.util.Pair
import com.atakmap.map.MapRenderer
import com.atakmap.map.layer.Layer
import com.atakmap.map.layer.opengl.GLAbstractLayer
import com.atakmap.map.layer.opengl.GLLayer2
import com.atakmap.map.layer.opengl.GLLayerSpi2
import com.atakmap.map.opengl.GLMapView
import com.atakmap.opengl.GLES20FixedPipeline
import com.atakmap.opengl.GLTexture
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.DoubleBuffer
import java.nio.FloatBuffer

class GLWeatherLayer(surface: MapRenderer, private val layer: WeatherLayer) : GLAbstractLayer(surface, layer) {

    data class FrameData(
        val numPoints: Int,
        var texture: GLTexture? = null,
        val textureBuffer: ByteBuffer = ByteBuffer.allocateDirect(Float.SIZE_BYTES * 2 * 4).order(ByteOrder.nativeOrder()),
    ) {
        val pointsBuffer: DoubleBuffer = ByteBuffer.allocateDirect(Double.SIZE_BYTES * 2 * numPoints).order(ByteOrder.nativeOrder()).asDoubleBuffer()
        val vertexBuffer: FloatBuffer = ByteBuffer.allocateDirect(Float.SIZE_BYTES * 2 * numPoints).order(ByteOrder.nativeOrder()).asFloatBuffer()

        fun update(layer: WeatherLayer, bitmap: Bitmap) {
            val width = 100
            val height = 100

            // if the bitmap data exceeds the bounds of the texture, allocate a
            // new instance
            if (texture == null) {
                texture = GLTexture(width, height, bitmap.config)
            }
            texture?.let {
                it.load(null, 0, 0, width, height)

                // note that while 'v' originates in the lower-left, by using an
                // upper-left origin we will have the GPU do the vertical flip for
                // us

                // update the texture coordinates to match the size of the new frame
                textureBuffer.clear()
                textureBuffer.putFloat(0.0f)
                textureBuffer.putFloat(0.0f)

                textureBuffer.putFloat(width.toFloat() / it.texWidth.toFloat())
                textureBuffer.putFloat(0.0f)

                textureBuffer.putFloat(width.toFloat() / it.texWidth.toFloat())
                textureBuffer.putFloat(height.toFloat() / it.texHeight.toFloat())

                textureBuffer.putFloat(0.0f)
                textureBuffer.putFloat(height.toFloat() / it.texHeight.toFloat())
                textureBuffer.flip()

                // update the corner coordinates for the frame; pairs are ordered
                // X, Y (longitude, latitude)
                pointsBuffer.clear()
                layer.metaShape.points.forEach { point ->
                    pointsBuffer.put(point.longitude)
                    pointsBuffer.put(point.latitude)
                }
                pointsBuffer.flip()

                // upload the bitmap data
                it.load(bitmap)
            }

        }
    }

    private lateinit var frame: FrameData

    companion object {
        val SPI: GLLayerSpi2 = object : GLLayerSpi2 {
            override fun create(surfaceLayerPair: Pair<MapRenderer, Layer>?): GLLayer2? {
                if (surfaceLayerPair?.second !is WeatherLayer) return null
                return GLWeatherLayer(surfaceLayerPair.first, surfaceLayerPair.second as WeatherLayer)
            }

            override fun getPriority(): Int = 1
        }
    }

    override fun init() {
        super.init()

        this.frame = FrameData(
            numPoints = layer.metaShape.points.size
        )

        // offload the actual update to the GL thread -- GL objects may only be
        // updated on the GL thread (e.g. texture).
        val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        bitmap.eraseColor(Color.GREEN)
        renderContext.queueEvent {
            try {
                frame.update(layer, bitmap)
            } finally {
                // cleanup the bitmap
                bitmap.recycle()
            }
        }
    }

    override fun drawImpl(view: GLMapView?) {
        // transform the frame's corner coordinates to GL x,y
        view?.forward(frame.pointsBuffer, frame.vertexBuffer)

        frame.texture?.draw(
            layer.metaShape.points.size,
            GLES20FixedPipeline.GL_FLOAT,
            frame.textureBuffer,
            frame.vertexBuffer
        )
    }
}