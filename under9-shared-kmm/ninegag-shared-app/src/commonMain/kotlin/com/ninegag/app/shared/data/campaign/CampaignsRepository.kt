package com.ninegag.app.shared.data.campaign

interface CampaignsRepository {
    fun updateCampaigns(list: List<CampaignDataModel>)
    fun getCampaigns(): List<CampaignDataModel>
}

class CampaignsRepositoryImpl(
    private val localCampaignsDatasource: LocalCampaignsDatasource
): CampaignsRepository {

    override fun updateCampaigns(list: List<CampaignDataModel>) {
        localCampaignsDatasource.updateCampaigns(list)
    }

    override fun getCampaigns(): List<CampaignDataModel> {
        return localCampaignsDatasource.getCampaigns()
    }
}