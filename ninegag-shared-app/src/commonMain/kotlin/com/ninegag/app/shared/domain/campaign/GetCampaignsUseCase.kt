package com.ninegag.app.shared.domain.campaign

import com.ninegag.app.shared.data.campaign.CampaignDataModel
import com.ninegag.app.shared.data.campaign.LocalCampaignsDatasource

class GetCampaignsUseCase(private val localCampaignsDatasource: LocalCampaignsDatasource) {
    fun execute(): List<CampaignDataModel> {
        return localCampaignsDatasource.getCampaigns()
    }
}