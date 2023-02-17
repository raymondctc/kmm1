package com.under9.shared.chat.api.model

import com.under9.shared.chat.api.basemodel.ApiBaseResponse
import kotlinx.serialization.Serializable

@Serializable
class ApiHeyStatus: ApiBaseResponse() {
    // Status ID
    var id: String = ""
    var userId: String = ""
    @Deprecated("Use hometown for hey v2")
    var gender: String = ""
    var hometown: String = ""
    var title: String = ""
    var timestamp: Long = 0
}
