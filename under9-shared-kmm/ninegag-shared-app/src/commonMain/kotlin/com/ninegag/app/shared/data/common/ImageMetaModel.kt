package com.ninegag.app.shared.data.common

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmField

@Serializable
data class ImageMetaModel(
    val url: String,
    val width: Int,
    val height: Int,
)