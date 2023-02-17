package com.ninegag.app.shared.config

abstract class BaseMixpanelConfig {
    abstract val apiKey: String
    abstract val apiEndpoint: String
}

object StgMixpanelConfig : BaseMixpanelConfig() {
    override val apiEndpoint: String = "https://mxpl.9gag-stg.com"
    override val apiKey: String = "f961794a4083c413afc3871aaffcc6c3"
}
object ReleaseMixpanelConfig : BaseMixpanelConfig() {
    override val apiEndpoint: String = "https://mxpl.9gag.com"
    override val apiKey: String = "6cb28d771797b5fc294c12a4655a28f2"
}