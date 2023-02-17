package com.under9.shared.core.util

import platform.Foundation.NSThread
import platform.Foundation.NSDate
import platform.Foundation.NSNumber
import platform.Foundation.timeIntervalSince1970
import platform.UIKit.UIDevice

actual object PlatformUtils {

    actual val platform: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion

    actual val currentTimeMillis: Long
        get() {
            return NSNumber(NSDate().timeIntervalSince1970 * 1000.0).longValue()
        }

    actual val currentThreadInfo: String
        get() {
            return NSThread.currentThread.toString()
        }

}