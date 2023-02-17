package com.ninegag.app.shared.data.setting

import com.ninegag.app.shared.infra.remote.base.ApiBaseResponse
import com.ninegag.app.shared.db.SharedNineGagDatabase
import com.ninegag.app.shared.util.checkAndReturnResult
import com.russhwolf.settings.Settings
import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import com.under9.shared.core.result.Result

interface RemoteSettingDatasource {
    suspend fun updateSetting(creatorUpdateStatus: Int): Result<ApiBaseResponse>
}

interface LocalSettingDatasource {

    /**
     * Has user performed something
     * @param actionKey The action name
     * @return Has this action been done before
     */
    fun beenDone(actionKey: String): Boolean

    /**
     * @param actionKey The name of the action
     * @return How many times has one perf. for a given action
     */
    fun beenDoneTimes(actionKey: String): Int
}

internal class RemoteSettingDatasourceImpl(private val httpClient: HttpClient): RemoteSettingDatasource {

    override suspend fun updateSetting(creatorUpdateStatus: Int): Result<ApiBaseResponse> {
        return try {
            Napier.d("updateSetting")
            val response = httpClient.submitForm(
                url = "settings",
                formParameters = Parameters.build {
                    append("creatorUpdateStatus", creatorUpdateStatus.toString())
                }
            ).body<ApiBaseResponse>()
            checkAndReturnResult(response)
        } catch (e: Exception) {
            Napier.e("error", e)
            Result.Error(e)
        }
    }
}

internal class LocalSettingDatasourceImpl(
    private val database: SharedNineGagDatabase,
    private val settings: Settings
): LocalSettingDatasource {

    override fun beenDone(actionKey: String): Boolean {
        val original = settings.getInt(actionKey, 0)
        if (original == 0) {
            settings.putInt(actionKey, 1)
        }
        return original == 1
    }

    override fun beenDoneTimes(actionKey: String): Int {
        val original = settings.getInt(actionKey, 0)
        val newCount = original + 1
        settings.putInt(actionKey, newCount)
        return original
    }
}