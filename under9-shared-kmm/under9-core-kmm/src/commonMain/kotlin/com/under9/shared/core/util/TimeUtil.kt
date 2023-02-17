package com.under9.shared.core.util

import com.soywiz.klock.DateTime
import com.soywiz.klock.KlockLocale
import com.soywiz.klock.locale.spanish
import com.under9.shared.res.MR
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.ResourceFormatted
import dev.icerock.moko.resources.desc.StringDesc
import io.github.aakira.napier.Napier
import kotlinx.datetime.*

object TimeUtil {

    /**
     * Specification
     * [https://github.com/9gag/Product/wiki/App-Behaviour-Guideline#time-stamp]
     */
    fun getAgoTimestamp(seconds: Long): StringDesc {
        val now = Clock.System.now()
        val nowEpoch = now.epochSeconds
        val dtInstant = Instant.fromEpochMilliseconds(seconds * 1000)
        val localDateTime = dtInstant.toLocalDateTime(TimeZone.currentSystemDefault())
        val diff = nowEpoch - seconds
        val period = dtInstant.periodUntil(now, TimeZone.currentSystemDefault())

        val dt = DateTime(seconds * 1000)
        val day = localDateTime.dayOfMonth
        val month = dt.month.localShortName
        val year = localDateTime.year.toString().substring(2, 4)

        val stringDesc = when {
            diff < 60 -> StringDesc.Resource(MR.strings.ago_justNow)
            diff in 60..3599 -> StringDesc.ResourceFormatted(MR.strings.ago_minute, period.minutes)
            diff in 3600..86399 -> StringDesc.ResourceFormatted(MR.strings.ago_hour, period.hours)
            diff in 86400..604799 -> StringDesc.ResourceFormatted(MR.strings.ago_day, period.days)
            else -> {
                val nowInstant = Instant.fromEpochSeconds(nowEpoch).toLocalDateTime(TimeZone.currentSystemDefault())
                if (nowInstant.year - localDateTime.year < 1) {
                    StringDesc.ResourceFormatted(MR.strings.all_generic, "$day $month")
                } else {
                    StringDesc.ResourceFormatted(MR.strings.all_generic, "$day $month $year")
                }
            }
        }
        return stringDesc
    }
}