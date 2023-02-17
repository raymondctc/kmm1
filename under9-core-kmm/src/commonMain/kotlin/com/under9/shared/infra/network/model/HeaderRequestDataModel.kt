package com.under9.shared.infra.network.model

data class HeaderRequestDataModel (
    val timestamp: String,
    val appId: String,
    val requestSignature: String,
    val packageName: String,
    val packageVersion: String,
    val deviceUUID: String,
    val deviceType: String,
    val userAgent: String
)
