package com.under9.shared.analytics.model

import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize


@Parcelize
data class ReferralInfo(
    val utmSource: String,
    val utmMedium: String,
    val utmCampaign: String? = null,
    val utmContent: String? = null,
    val utmTerm: String? = null
): Parcelable {

    fun isEmpty(): Boolean {
        return utmSource.isEmpty() && (utmCampaign ?: "").isEmpty() && utmMedium.isEmpty()
    }
}