package com.under9.shared.analytics.model

import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize

@Parcelize
data class PermutivePageInfo(
    val pageTitle: String,
    val pageUrl: String
): Parcelable