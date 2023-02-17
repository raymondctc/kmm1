package com.under9.shared.chat.api.model

import com.under9.shared.chat.api.basemodel.ApiBaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class ApiLoginResponse (
   val data: Data? = null
): ApiBaseResponse() {
    @Serializable
    data class Data (
        val userToken: String,
        val tokenExpiry: Long = 0,
        val secondsTillExpiry: Long = 0,
        val user: ApiHeyAccount,
        val chat: ApiChat
    )

    @Serializable
    data class ApiChat(
        val chatUserId: String, // stream user ID
        val hasChat: Boolean // Whether the user has active chats
    )
}

