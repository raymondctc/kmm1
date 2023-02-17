package com.under9.shared.chat.domain.auth

import com.under9.shared.chat.domain.serializablemodel.StreamAuthDomainModel

data class HeyAccountDomainModel (
    val userId: String,
    @Deprecated("Use hometown for hey v2")
    val gender: String,
    val hometown: String,
    val notificationTopic: String?,
    val userToken: String,
    val tokenExpiry: Long,
    val secondTillExpiry: Long,
    val streamUserId: String?,
    val hasChat: Boolean,
)