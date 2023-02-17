package com.ninegag.app.shared.config

abstract class BaseHelpshiftConfig {
    abstract val appId: String
    abstract val apiKey: String
    val domain: String = "9gag.helpshift.com"
}

abstract class BaseStgHelpshiftConfig : BaseHelpshiftConfig()
abstract class BaseReleaseHelpshiftConfig : BaseHelpshiftConfig()

object StgIosHelpshiftConfig : BaseStgHelpshiftConfig() {
    override val appId: String = "9gag_platform_20210506074734541-8c30ec93d9fd030"
    override val apiKey: String = "9fa597fdbc96c7458808ee2ba3389fae"
}

object StgAndroidHelpshiftConfig : BaseStgHelpshiftConfig() {
    override val appId: String = "9gag_platform_20210504060841983-795330956e7d1fc"
    override val apiKey: String = "4149117368f27200e4be55db30d22ca8"
}

object ReleaseIosHelpshiftConfig : BaseReleaseHelpshiftConfig() {
    override val appId: String = "9gag_platform_20210504061132147-32219e74ee01d57"
    override val apiKey: String = "4149117368f27200e4be55db30d22ca8"
}

object ReleaseAndroidHelpshiftConfig : BaseReleaseHelpshiftConfig() {
    override val appId: String = "9gag_platform_20210504060841983-795330956e7d1fc"
    override val apiKey: String = "4149117368f27200e4be55db30d22ca8"
}