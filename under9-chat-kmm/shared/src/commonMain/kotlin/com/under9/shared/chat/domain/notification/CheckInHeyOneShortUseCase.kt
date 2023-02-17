package com.under9.shared.chat.domain.notification

import com.under9.shared.core.UseCase
import com.under9.shared.core.result.Result
import com.under9.shared.infra.db.Preferences
import com.under9.shared.infra.db.putInt
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayAt

/**
 * Update current hey visit day
 * */
class CheckInHeyOneShortUseCase(
    ioDispatcher: CoroutineDispatcher,
    private val preferences: Preferences
): UseCase<Unit, Result<Unit>>(ioDispatcher) {

    override suspend fun execute(parameters: Unit): Result<Unit> {
        val today: LocalDate = Clock.System.todayAt(TimeZone.currentSystemDefault())
        preferences.putInt(HeyVisitDayDiffOneShortUseCase.PREF_KEY_LAST_HEY_VISIT_DAY, today.dayOfMonth)

        return Result.Success(Unit)
    }
}