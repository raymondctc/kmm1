package com.under9.shared.internal

/**
 * Formatting tools to turn [io.github.aakira.napier.Napier]
 * to rlog's format
 */
object RLogToNapierFormatHelper {
    fun formatMessage(key: String, message: String): String {
        return "$key,$message"
    }
}