package com.under9.shared.core.util

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

object FlowUtil {

    @ExperimentalTime
    fun tickerFlow(period: Duration, initialDelay: Duration = Duration.ZERO) = flow {
        delay(initialDelay)
        while (true) {
            emit(Unit)
            delay(period)
        }
    }

    @ExperimentalTime
    fun delayFlow(period: Duration) = flow {
        delay(period)
        emit(Unit)
    }
}