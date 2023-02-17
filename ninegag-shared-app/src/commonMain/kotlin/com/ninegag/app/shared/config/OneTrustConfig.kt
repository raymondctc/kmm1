package com.ninegag.app.shared.config

abstract class BaseOneTrustConfig {
    abstract val appId: String
    val domain: String = "cdn-apac.onetrust.com"
}

object StgIosOneTrustConfig : BaseOneTrustConfig() {
    override val appId: String = "1d0c3be4-4cf0-42e0-ae41-5beac99ba337-test"
}
object StgAndroidOneTrustConfig : BaseOneTrustConfig() {
    override val appId: String = "bd1fcb49-77bb-437d-b955-9f93d4bb472d-test"
}


object ReleaseIosOneTrustConfig : BaseOneTrustConfig() {
    override val appId: String = "1d0c3be4-4cf0-42e0-ae41-5beac99ba337"
}
object ReleaseAndroidOneTrustConfig : BaseOneTrustConfig() {
    override val appId: String = "bd1fcb49-77bb-437d-b955-9f93d4bb472d"
}
