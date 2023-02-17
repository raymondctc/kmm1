package com.ninegag.app.shared.data.setting

import com.ninegag.app.shared.infra.remote.base.ApiBaseResponse
import com.under9.shared.core.result.Result

interface SettingRepository {
    suspend fun updateSetting(creatorUpdateStatus: Int): Result<ApiBaseResponse>
    fun beenDone(actionKey: String): Boolean
}

class SettingRepositoryImpl(
    private val localSettingDatasource: LocalSettingDatasource,
    private val remoteSettingDatasource: RemoteSettingDatasource
) : SettingRepository {

    override suspend fun updateSetting(creatorUpdateStatus: Int): Result<ApiBaseResponse> {
        return remoteSettingDatasource.updateSetting(creatorUpdateStatus)
    }

    override fun beenDone(actionKey: String): Boolean {
        return localSettingDatasource.beenDone(actionKey)
    }
}