package com.ninegag.app.shared.data.setting

import com.ninegag.app.shared.domain.tag.UpdateFavHiddenRecentStatusUseCase

object AppSettingKeys {
    val TAG_FAV_NAV_TAG_ITEM = "${UpdateFavHiddenRecentStatusUseCase::class.qualifiedName}.favorite"
    val TAG_FAV_NAV_TAG_MIGRATION = "${UpdateFavHiddenRecentStatusUseCase::class.qualifiedName}.migrate"
}