package com.ninegag.app.shared.config

abstract class BaseNotificationConfig {
    abstract val apiEndpoint: String
}

object StgNotificationConfig : BaseNotificationConfig() {
    override val apiEndpoint: String = "https://notif.9jokes.com"
}

object ReleaseNotificationConfig : BaseNotificationConfig() {
    override val apiEndpoint: String = "https://notif.9gag.com"
}