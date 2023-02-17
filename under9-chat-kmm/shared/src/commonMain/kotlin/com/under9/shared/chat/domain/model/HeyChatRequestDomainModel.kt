package com.under9.shared.chat.domain.model

import com.under9.shared.chat.api.model.ApiHeyChatAccept
import com.under9.shared.chat.api.model.ApiHeyStatus

data class HeyChatRequestDomainModel (
        val id: String,
        val timestamp: Long,
        val ttl: Int,
        val requestStatus: ApiHeyStatus?,
        val acceptStatus: ApiHeyStatus?)