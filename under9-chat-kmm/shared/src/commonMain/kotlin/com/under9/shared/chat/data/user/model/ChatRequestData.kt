package com.under9.shared.chat.data.user.model

import kotlinx.serialization.Serializable

@Serializable
data class ChatRequestData(
    val requestId: String,
    val channelId: String? = null,
    val sender: String,
    val recipient: String,
    val senderHometown: String,
    val recipientHometown: String,
    val title: String
)