package com.ninegag.app.shared.domain

import com.ninegag.app.shared.appUserAgentConfig
import com.ninegag.app.shared.config.CurrentPlatform
import com.ninegag.app.shared.config.PlatformEnum
import com.ninegag.app.shared.config.UserAgentConfig
import com.russhwolf.settings.Settings
import com.under9.shared.core.util.AuthUtil
import com.under9.shared.core.util.PlatformUtils
import com.under9.shared.infra.db.getString
import com.under9.shared.infra.db.putString
import com.under9.shared.infra.network.HttpHeaderValueManager
import com.under9.shared.infra.network.model.HeaderRequestDataModel

// TODO Remove the class when the AppMeta, runtime value, aoc are migrated to KMM
class GagHttpHeaderValueManager(
    private val settings: Settings,
    private val userAgentConfig: UserAgentConfig
) {
    companion object {
        const val KEY_APP_ID = "ninegag_header_app_id"
        const val KEY_PACKAGE_NAME = "ninegag_header_package_name"
        const val KEY_PACKAGE_VERSION = "ninegag_header_package_version"
        const val KEY_DEVICE_UUID = "ninegag_header_device_uuid"
        const val KEY_DEVICE_TYPE = "ninegag_header_device_type"
        const val KEY_USER_AGENT = "ninegag_header_user_agent"
        const val KEY_NINE_GAG_TOKEN = "ninegag_nine_gag_token"
    }

    fun createRequestHeader(): HeaderRequestDataModel {
        val timestamp = PlatformUtils.currentTimeMillis
        val packageName = settings.getString(KEY_PACKAGE_NAME, "")
        val deviceUUID = settings.getString(KEY_DEVICE_UUID, "")
        if (deviceUUID.isEmpty()) {
            settings.putString(KEY_DEVICE_UUID, appUserAgentConfig.deviceUUIDProvider())
        }

        val userAgent = settings.getString(KEY_USER_AGENT, "")
        if (userAgent.isEmpty()) {
            settings.putString(KEY_USER_AGENT, appUserAgentConfig.userAgentProvider())
        }

        val packageVersion = settings.getString(KEY_PACKAGE_VERSION, "")
        val appId = settings.getString(KEY_APP_ID, "")
        val deviceType = settings.getString(KEY_DEVICE_TYPE, "")

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

    fun updateRequestHeaderValue(
        appId: String,
        packageName: String,
        packageVersion: String,
        deviceType: String
    ) {
        with(settings) {
            putString(KEY_APP_ID, appId)
            putString(KEY_PACKAGE_NAME, packageName)
            putString(KEY_PACKAGE_VERSION, packageVersion)
            putString(KEY_DEVICE_TYPE, deviceType)
        }
    }

    fun getToken(): String {
        return settings.getString(KEY_NINE_GAG_TOKEN, "")
    }


    fun setToken(token: String) {
        settings.putString(KEY_NINE_GAG_TOKEN, token)
    }

}