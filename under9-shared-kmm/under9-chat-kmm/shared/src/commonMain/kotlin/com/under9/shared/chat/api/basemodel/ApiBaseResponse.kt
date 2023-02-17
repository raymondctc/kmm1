package com.under9.shared.chat.api.basemodel

import kotlinx.serialization.Serializable

@Serializable
open class ApiBaseResponse {
    var meta: Meta? = null

    @Serializable
    class Meta {
        var sid: String? = null
        var status: String? = null
        var errorCode: String? = null
        var errorMessage: String? = null
        var timestamp: Long = 0
    }

    fun success(): Boolean {
        if (meta == null) return false
        return if (meta!!.status.isNullOrBlank()) false else meta!!.status == STATUS_SUCCESS
    }

    val errorMessage: String?
        get() {
            if (meta == null) return ""
            return if (meta!!.errorMessage.isNullOrBlank()) "" else meta!!.errorMessage
        }

    companion object {
        private const val STATUS_SUCCESS = "Success"
    }
}
