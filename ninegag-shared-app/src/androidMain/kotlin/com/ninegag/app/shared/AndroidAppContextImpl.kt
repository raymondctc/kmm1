package com.ninegag.app.shared

import android.content.Context

class AndroidAppContextImpl(
    private val context: Context
): AppContext<Context> {
    override fun getAppContext(): Context {
        return context
    }
}