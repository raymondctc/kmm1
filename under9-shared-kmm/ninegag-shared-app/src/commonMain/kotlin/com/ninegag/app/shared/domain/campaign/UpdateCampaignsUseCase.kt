package com.ninegag.app.shared.domain.campaign

import com.ninegag.app.shared.data.campaign.CampaignDataModel
import com.ninegag.app.shared.data.campaign.LocalCampaignsDatasource

class UpdateCampaignsUseCase(private val localCampaignsDatasource: LocalCampaignsDatasource) {
    fun execute(param: Param) {
        localCampaignsDatasource.updateCampaigns(param.list)
    }

    data class Param(
        val list: List<CampaignDataModel>
    )
}