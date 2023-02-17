package com.ninegag.app.shared.data.campaign

import com.ninegag.app.shared.data.common.ImageMetaModel
import kotlinx.serialization.Serializable

@Serializable
data class CampaignDataModel(
    val name: String,
    val link: String,
    val images: Images
) {
    @Serializable
    data class Images(
        val banner: ImageMetaModel,
        val card: ImageMetaModel
    )
}