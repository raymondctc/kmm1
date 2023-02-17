package com.under9.shared.infra.network

import com.under9.shared.core.util.*
import com.under9.shared.infra.db.Preferences
import com.under9.shared.infra.db.getString
import com.under9.shared.infra.db.putString
import com.under9.shared.infra.network.model.HeaderRequestDataModel

class HttpHeaderValueManager(
        private val preferences: Preferences
) {

    companion object {
        const val KEY_APP_ID = "kmm_core_header_app_id"
        const val KEY_PACKAGE_NAME = "kmm_core_header_package_name"
        const val KEY_PACKAGE_VERSION = "kmm_core_header_package_version"
        const val KEY_DEVICE_UUID = "kmm_core_header_device_uuid"
        const val KEY_DEVICE_TYPE = "kmm_core_header_device_type"
        const val KEY_USER_AGENT = "kmm_core_header_user_agent"
    }

    fun createRequestHeader(): HeaderRequestDataModel {
        val timestamp = PlatformUtils.currentTimeMillis
        val packageName = preferences.getString(KEY_PACKAGE_NAME)
        val deviceUUID = preferences.getString(KEY_DEVICE_UUID)
        val packageVersion = preferences.getString(KEY_PACKAGE_VERSION)
        val appId = preferences.getString(KEY_APP_ID)
        val deviceType = preferences.getString(KEY_DEVICE_TYPE)
        val userAgent = preferences.getString(KEY_USER_AGENT)

        return HeaderRequestDataModel(
                timestamp = timestamp.toString(),
                appId = appId,
                requestSignature = AuthUtil.getAppSignature(timestamp, packageName, deviceUUID),
                packageName = packageName,
                packageVersion = packageVersion,
                deviceUUID = deviceUUID,
                deviceType = deviceType,
                userAgent = userAgent
        )
    }

    fun updateRequestHeaderValue(appId: String,
                                 packageName: String,
                                 packageVersion: String,
                                 deviceUUID: String,
                                 deviceType: String,
                                 userAgent: String) {
        with(preferences) {
            putString(KEY_APP_ID, appId)
            putString(KEY_PACKAGE_NAME, packageName)
            putString(KEY_PACKAGE_VERSION, packageVersion)
            putString(KEY_DEVICE_UUID, deviceUUID)
            putString(KEY_DEVICE_TYPE, deviceType)
            putString(KEY_USER_AGENT, userAgent)
        }
    }
}