package com.under9.shared.infra.db

import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.core.content.edit

actual typealias Preferences = Context

actual fun Preferences.putInt(key: String, value: Int) {
    this.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE).edit {
        putInt(key, value)
    }
}

actual fun Preferences.getInt(key: String, defaultValue: Int) : Int {
    val prefs: SharedPreferences = this.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE)
    return prefs.getInt(key, -1)
}

actual fun Preferences.putFloat(key: String, value: Float) {
    this.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE).edit {
        putFloat(key, value)
    }
}

actual fun Preferences.getFloat(key: String, defaultValue: Float): Float {
    val prefs: SharedPreferences = this.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE)
    return prefs.getFloat(key, defaultValue)
}

actual fun Preferences.putLong(key: String, value: Long) {
    this.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE).edit {
        putLong(key, value)
    }
}

actual fun Preferences.getLong(key: String, defaultValue: Long): Long {
    val prefs: SharedPreferences = this.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE)
    return prefs.getLong(key, defaultValue)
}

actual fun Preferences.putString(key: String, value: String) {
    this.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE).edit {
        putString(key, value)
    }
}

actual fun Preferences.getString(key: String, defaultValue: String): String {
    val prefs: SharedPreferences = this.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE)
    return prefs.getString(key, defaultValue)?: defaultValue
}

actual fun Preferences.putBoolean(key: String, value: Boolean) {
    this.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE).edit {
        putBoolean(key, value)
    }
}

actual fun Preferences.getBoolean(key: String, defaultValue: Boolean): Boolean {
    val prefs: SharedPreferences = this.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE)
    return prefs.getBoolean(key, defaultValue)
}





