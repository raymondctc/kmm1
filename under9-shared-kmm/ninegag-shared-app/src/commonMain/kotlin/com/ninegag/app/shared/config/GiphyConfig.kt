package com.ninegag.app.shared.config

abstract class BaseGiphyConfig {
    abstract val apiKey: String
}

object StgIosGiphyConfig : BaseGiphyConfig() {
    override val apiKey: String = "XeovS2CSANmOut6QuwZmS8rjBotH8zaE"
}
object StgAndroidGiphyConfig : BaseGiphyConfig() {
    override val apiKey: String = "XeovS2CSANmOut6QuwZmS8rjBotH8zaE"
}

object ReleaseIosGiphyConfig : BaseGiphyConfig() {
    override val apiKey: String = "C4kkJ1zBK5dgLNHmqjakudwrpGKsE6sB"
}
object ReleaseAndroidGiphyConfig : BaseGiphyConfig() {
    override val apiKey: String = "7zJ8oinU2pqh0WpRHYey6H6r4ovtPdLX"
}