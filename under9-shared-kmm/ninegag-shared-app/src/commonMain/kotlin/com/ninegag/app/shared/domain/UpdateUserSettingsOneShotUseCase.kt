package com.ninegag.app.shared.domain

import com.ninegag.app.shared.data.setting.SettingRepository
import com.under9.shared.core.UseCase
import kotlinx.coroutines.CoroutineDispatcher

class UpdateUserSettingsOneShotUseCase(
    private val settingRepository: SettingRepository,
    ioDispatcher: CoroutineDispatcher
): UseCase<UserSettings, Boolean>(ioDispatcher) {
    override suspend fun execute(parameters: UserSettings): Boolean {
        val result = settingRepository.updateSetting(parameters.creatorUpdateStatus)
        return result.isSuccess()
    }
}

data class UserSettings(
    val creatorUpdateStatus: Int
)