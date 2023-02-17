package com.under9.shared.chat.api.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiNotification(
    val topic: String
)