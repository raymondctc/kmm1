package com.under9.shared.chat.api.model

import com.under9.shared.chat.api.basemodel.ApiBaseResponse
import kotlinx.serialization.Serializable

@Serializable
class ApiHeyGender: ApiBaseResponse() {
    var gender: String = ""
}