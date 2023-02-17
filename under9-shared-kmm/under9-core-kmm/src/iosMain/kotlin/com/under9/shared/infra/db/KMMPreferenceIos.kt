package com.under9.shared.infra.db

import kotlinx.cinterop.ObjCObjectBase
import platform.Foundation.NSUserDefaults
import platform.darwin.NSObject

actual typealias Preferences = ObjCObjectBase

actual fun Preferences.putInt(key: String, value: Int) {
    NSUserDefaults.standardUserDefaults.setInteger(value.toLong(), key)
}

actual fun Preferences.getInt(key: String, defaultValue: Int) : Int {
    return NSUserDefaults.standardUserDefaults.integerForKey(key).toInt()
}

actual fun Preferences.putFloat(key: String, value: Float) {
    NSUserDefaults.standardUserDefaults.setFloat(value, key)
}

actual fun Preferences.getFloat(key: String, defaultValue: Float): Float {
    return NSUserDefaults.standardUserDefaults.floatForKey(key)
}

actual fun Preferences.putLong(key: String, value: Long) {
    NSUserDefaults.standardUserDefaults.setInteger(value, key)
}

actual fun Preferences.getLong(key: String, defaultValue: Long): Long {
    return NSUserDefaults.standardUserDefaults.integerForKey(key)
}

actual fun Preferences.putString(key: String, value: String) {
    NSUserDefaults.standardUserDefaults.setObject(value, key)
}

actual fun Preferences.getString(key: String, defaultValue: String): String {
    return NSUserDefaults.standardUserDefaults.stringForKey(key) ?: defaultValue
}

actual fun Preferences.putBoolean(key: String, value: Boolean) {
    NSUserDefaults.standardUserDefaults.setBool(value, key)
}

actual fun Preferences.getBoolean(key: String, defaultValue: Boolean): Boolean {
    return NSUserDefaults.standardUserDefaults.boolForKey(key)
}