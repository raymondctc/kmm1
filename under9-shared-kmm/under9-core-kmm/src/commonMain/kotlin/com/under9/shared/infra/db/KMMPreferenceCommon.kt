package com.under9.shared.infra.db

expect abstract class Preferences

expect fun Preferences.putInt(key: String, value: Int)
expect fun Preferences.getInt(key: String, defaultValue: Int = -1): Int
//
expect fun Preferences.putFloat(key: String, value: Float)
expect fun Preferences.getFloat(key: String, defaultValue: Float = -1F): Float

expect fun Preferences.putLong(key: String, value: Long)
expect fun Preferences.getLong(key: String, defaultValue: Long = -1L): Long

expect fun Preferences.putString(key: String, value: String)
expect fun Preferences.getString(key: String, defaultValue: String = ""): String

expect fun Preferences.putBoolean(key: String, value: Boolean)
expect fun Preferences.getBoolean(key: String, defaultValue: Boolean = false): Boolean

//expect fun Preference.remove(key: String)
//expect fun Preference.clear()
//
//expect fun Preference.hasKey(key: String): Boolean

const val PREFERENCES_NAME = "kmm_pref"
