package com.ninegag.app.shared.config

abstract class BaseRLogConfig {
    abstract val appId: String
    abstract val apiEndpoint: String
}

abstract class BaseStgRLogConfig : BaseRLogConfig() {
    override val apiEndpoint: String = "https://rlog.9jokes.com"
}

abstract class BaseReleaseRLogConfig : BaseRLogConfig() {
    override val apiEndpoint: String = "https://rlog.9gag.com"
}

object StgIosRLogConfig : BaseStgRLogConfig() {
    override val appId: String = "ios.9gag.main.stg"
}
object StgAndroidRLogConfig : BaseStgRLogConfig() {
    override val appId: String = "android.9gag.main.dev"
}

object ReleaseIosRLogConfig : BaseReleaseRLogConfig() {
    override val appId: String = "ios.9gag.main.release"
}
object ReleaseAndroidRLogConfig : BaseReleaseRLogConfig() {
    override val appId: String = "android.9gag.main.release"
}