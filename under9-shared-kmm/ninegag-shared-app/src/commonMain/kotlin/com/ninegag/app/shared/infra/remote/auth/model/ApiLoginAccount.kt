package com.ninegag.app.shared.infra.remote.auth.model

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmField

// TODO: Partially done, need to be implemented
@Serializable
data class ApiLoginAccount(
    @JvmField val userId: String,
    @JvmField val accountId: String,
    @JvmField val loginName: String
)