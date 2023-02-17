package com.under9.shared.chat.api.model

import com.under9.shared.chat.api.basemodel.ApiBaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class ApiHeyAccount(
    val userId: String,
    @Deprecated("Use hometown for hey v2")
    val gender: String = "",
    val hometown: String = "",
    val notification: ApiNotification
): ApiBaseResponse()
