package com.ninegag.app.shared.infra.remote.interest.model

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmField

@Serializable
data class ApiInterest(
    @JvmField val name: String = "",
    @JvmField val listType: String = "",
    @JvmField val imageUrl: String = "",
    @JvmField val webpUrl: String = ""
)