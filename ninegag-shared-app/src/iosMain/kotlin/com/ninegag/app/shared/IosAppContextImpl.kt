package com.ninegag.app.shared

import platform.Foundation.NSBundle

class IosAppContextImpl(
    private val nsBundle: NSBundle
): AppContext<NSBundle> {
    override fun getAppContext(): NSBundle {
        return nsBundle
    }
}