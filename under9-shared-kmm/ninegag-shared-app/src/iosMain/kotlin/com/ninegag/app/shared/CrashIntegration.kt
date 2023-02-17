package com.ninegag.app.shared

import co.touchlab.crashkios.CrashHandler
import co.touchlab.crashkios.setupCrashHandler

/**
 * Initialize Crahslytics
 *
 * @param handler See [co.touchlab.crashkios.CrashHandler]
 */
fun crashInit(handler: CrashHandler){
    setupCrashHandler(handler)
}