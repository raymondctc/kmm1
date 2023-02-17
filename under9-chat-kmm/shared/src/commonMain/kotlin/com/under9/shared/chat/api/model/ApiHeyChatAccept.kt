package com.under9.shared.chat.api.model

import com.under9.shared.chat.api.basemodel.ApiBaseResponse
import kotlinx.serialization.Serializable

@Serializable
class ApiHeyChatAccept : ApiBaseResponse() {
    var data: Data? = null

    @Serializable
    class Data {
        var chat: Chat? = null
    }

    @Serializable
    class Chat {
        var id: String = ""
        var timestamp: Long = 0
        var requestStatus: ApiHeyStatus? = null // requester hey status
        var acceptStatus: ApiHeyStatus? = null // accepter hey status
    }
}