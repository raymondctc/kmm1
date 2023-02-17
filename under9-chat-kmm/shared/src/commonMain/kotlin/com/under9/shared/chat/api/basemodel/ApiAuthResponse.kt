package com.under9.shared.chat.api.basemodel

import kotlinx.serialization.Serializable

@Serializable
class ApiAuthResponse : ApiBaseResponse() {
    var data: Data? = null

    @Serializable
    class Data {
        var userToken: String? = null
        var tokenExpiry: Long = 0
        var secondsTillExpiry: Long = 0
    }
}
