package com.under9.shared.infra.network

import com.under9.shared.core.util.PlatformUtils
import com.under9.shared.core.util.AuthUtil
import com.under9.shared.infra.network.model.HeaderRequestDataModel
import kotlinx.serialization.Serializable

@Serializable
class RequestHeaderFactory(
        private val appId: String,
        private val packageName: String,
        private val packageVersion: String,
        private val deviceUUID: String,
        private val isDebug: Boolean,
        private val deviceType: String,
        private val userAgent: String
) {

    fun createRequestHeader(): HeaderRequestDataModel {
        val timestamp = PlatformUtils.currentTimeMillis

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

}