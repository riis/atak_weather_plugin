package com.riis.atakweather.plugin.context

import android.content.Context

class SimpleContextManager(private val context: Context): IContextManager {
    override fun getContext(): Context {
        return context
    }
}