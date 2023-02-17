package com.under9.shared.chat.domain.serializablemodel

import kotlinx.serialization.*

@Serializable
data class StreamAuthDomainModel(
        val userId: String, // stream user id
        val token: String, // stream token
        val hasChat: Boolean // user have active chats?
)