package com.ninegag.app.shared.infra.remote.auth.model

import com.ninegag.app.shared.infra.remote.base.ApiBaseResponse
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmField

@Serializable
data class ApiAuthResponse(
    @JvmField val data: Data,
    @JvmField val commentAuth: CommentAuth?,
    @JvmField val notifAuth: NotifAuth?
) : ApiBaseResponse() {

    /**
     * TODO: We may need a custom serializer to avoid this nullable fields declaration
     * For incorrect response, data object is empty, so fall fields can be nullable
     */
    @Serializable
    data class Data(
        @JvmField val userToken: String?,
        @JvmField val tokenExpiry: Int?,
        @JvmField val secondsTillExpiry: Int?,
        @JvmField val user: ApiLoginAccount?
    )

    @Serializable
    data class CommentAuth(
        @JvmField val type: String,
        @JvmField val authHash: String,
        @JvmField val expiryTs: Int
    )

    @Serializable
    data class NotifAuth(
        @JvmField val readStateParams: String
    )
}