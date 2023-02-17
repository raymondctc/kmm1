package com.under9.shared.chat.domain.notification

import com.under9.shared.core.UseCase
import com.under9.shared.infra.db.Preferences
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineDispatcher
import com.under9.shared.core.result.Result
import com.under9.shared.infra.db.getInt
import kotlinx.datetime.*

/**
 * UseCase that shows the notification is available to show
 * Rules: Show the notification every day after 00:00am
 * */
class HeyVisitDayDiffOneShortUseCase(
    private val ioDispatcher: CoroutineDispatcher,
    private val preferences: Preferences
): UseCase<Unit, Boolean>(ioDispatcher) {

    companion object {
        val PREF_KEY_LAST_HEY_VISIT_DAY = "last_hey_visit_day"
    }

    override suspend fun execute(parameters: Unit): Boolean {
        val today: LocalDate = Clock.System.todayAt(TimeZone.currentSystemDefault())
        val lastDay = preferences.getInt(PREF_KEY_LAST_HEY_VISIT_DAY, -1)

        return today.dayOfMonth - lastDay != 0
    }
}