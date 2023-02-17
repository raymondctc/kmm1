package com.under9.shared.chat.api.model

import com.under9.shared.chat.api.basemodel.ApiBaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class ApiHeyChatToken(
    val data: Data? = null
) : ApiBaseResponse() {
    @Serializable
    data class Data (
        val token: String
    )
}