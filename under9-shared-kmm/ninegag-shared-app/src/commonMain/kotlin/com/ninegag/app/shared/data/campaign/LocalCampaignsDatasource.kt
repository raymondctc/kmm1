package com.ninegag.app.shared.data.campaign

import com.russhwolf.settings.Settings
import com.under9.shared.core.U9Json

interface LocalCampaignsDatasource {
    fun updateCampaigns(list: List<CampaignDataModel>)
    fun getCampaigns(): List<CampaignDataModel>
}

class LocalCampaignsDataSourceImpl(
    private val settings: Settings
): LocalCampaignsDatasource {
    companion object {
        private const val CAMPAIGNS_SETTING_KEY = "campaigns_setting"
    }

    override fun updateCampaigns(list: List<CampaignDataModel>) {
        val listString = U9Json.encodeToString(list)
        settings.putString(CAMPAIGNS_SETTING_KEY, listString)
    }

    override fun getCampaigns(): List<CampaignDataModel> {
        val campaignsStr = settings.getString(CAMPAIGNS_SETTING_KEY, "")
        return if (campaignsStr.isEmpty()) {
            emptyList()
        } else {
            U9Json.decodeFromString(campaignsStr)
        }
    }
}