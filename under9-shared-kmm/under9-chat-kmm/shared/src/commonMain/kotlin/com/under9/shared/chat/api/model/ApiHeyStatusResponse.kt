package com.under9.shared.chat.api.model

import com.under9.shared.chat.api.basemodel.ApiBaseResponse
import kotlinx.serialization.Serializable

@Serializable
class ApiHeyStatusResponse: ApiBaseResponse() {
    var data: Data? = null

    @Serializable
    class Data {
        var status: ApiHeyStatus? = null
    }
}