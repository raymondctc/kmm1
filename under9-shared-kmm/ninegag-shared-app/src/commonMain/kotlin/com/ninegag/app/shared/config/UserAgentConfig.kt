package com.ninegag.app.shared.config

data class UserAgentConfig(
    val packageName: String,
    val packageVersion: String,
    val deviceUUIDProvider: () -> String,
    val userAgentProvider: () -> String,
    val enableQUICProvider: () -> Boolean
)