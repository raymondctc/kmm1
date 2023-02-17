package com.under9.shared.chat.api.model

import com.under9.shared.chat.api.basemodel.ApiBaseResponse
import kotlinx.serialization.Serializable

@Serializable
class ApiHeyFeedResponse: ApiBaseResponse() {
    var data: Data? = null

    @Serializable
    class Data {
        var statuses = arrayOf<ApiHeyStatus>()
    }
}