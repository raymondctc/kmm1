package com.ninegag.app.shared.infra.remote.tag.model

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmField

@Serializable
class ApiFeaturedAds(@JvmField val position: Int,
                     @JvmField val width: Int,
                     @JvmField val height: Int,
                     @JvmField val url: String,
                     @JvmField val render: String) {

    companion object {
        const val RENDER_IMA_ORA = "ora"
        const val RENDER_IMA_CUSTOM = "ima"
        const val RENDER_EMBED_WEBVIEW = "iframe"
    }

    override fun toString(): String {
        return "ApiFeaturedAds@${hashCode()}, position={$position}, w={$width}, h={$height}, url={$url}, render={$render}"
    }
}