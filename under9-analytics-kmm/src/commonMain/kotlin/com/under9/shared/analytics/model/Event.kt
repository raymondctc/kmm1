package com.under9.shared.analytics.model

data class Event(
    val name: String,
    val params: Map<String, Any?>? = null
)