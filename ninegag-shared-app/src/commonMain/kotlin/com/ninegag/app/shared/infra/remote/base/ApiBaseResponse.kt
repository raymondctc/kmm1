package com.ninegag.app.shared.infra.remote.base

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmField

@Serializable
open class ApiBaseResponse {
    @JvmField var meta: Meta? = null

    @Serializable
    data class Meta(
        @JvmField val sid: String? = null,
        @JvmField val status: String? = null,
        @JvmField val errorCode: String? = null,
        @JvmField val errorMessage: String? = null,
        @JvmField val timestamp: Long = 0
    ) {
        override fun toString(): String {
            return "status=$status"
        }
    }

    fun success(): Boolean {
        if (meta == null) return false
        return if (meta!!.status.isNullOrBlank()) false else meta!!.status == STATUS_SUCCESS
    }

    val errorMessage: String
        get() {
            if (meta == null) return ""
            return if (meta!!.errorMessage.isNullOrBlank()) "" else meta!!.errorMessage!!
        }

    companion object {
        private const val STATUS_SUCCESS = "Success"
    }
}
