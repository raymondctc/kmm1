package com.ninegag.app.shared.config

abstract class BaseCommentConfig {
    abstract val appId: String
    abstract val apiEndpoint: String
    abstract val cdnApiEndpoint: String
}

abstract class BaseStgCommentConfig : BaseCommentConfig() {
    override val appId: String = "a_8ffed3d619270a8e605654f8023db7958063d458"
    override val apiEndpoint: String = "https://comment.9jokes.com"
    override val cdnApiEndpoint: String = "https://comment.9jokes.com"
}

abstract class BaseReleaseCommentConfig : BaseCommentConfig() {
    override val appId: String = "a_dd8f2b7d304a10edaf6f29517ea0ca4100a43d1b"
    override val apiEndpoint: String = "https://comment.9gag.com"
    override val cdnApiEndpoint: String = "https://comment.9gag.com"
}

object StgIosCommentConfig : BaseStgCommentConfig()
object StgAndroidCommentConfig : BaseStgCommentConfig()

object ReleaseIosCommentConfig : BaseReleaseCommentConfig()
object ReleaseAndroidCommentConfig : BaseReleaseCommentConfig()