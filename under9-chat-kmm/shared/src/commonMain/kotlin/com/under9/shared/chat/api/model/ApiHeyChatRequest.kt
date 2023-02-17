package com.under9.shared.chat.api.model

import com.under9.shared.chat.api.basemodel.ApiBaseResponse
import kotlinx.serialization.Serializable

@Serializable
class ApiHeyChatRequest : ApiBaseResponse() {

    var data: Data? = null

    @Serializable
    class Data {
        var request: Request? = null
    }

    @Serializable
    class Request {
        var id: String = ""
        var timestamp: Long = 0
        var ttl: Int = 0
        var requestStatus: ApiHeyStatus? = null // requester hey status
        var acceptStatus: ApiHeyStatus? = null // accepter hey status
    }
}