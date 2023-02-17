package com.under9.shared.core.result

import com.under9.shared.core.result.Result.Success
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val throwable: Throwable) : Result<Nothing>()
    object Loading : Result<Nothing>()
    object Cancel : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$throwable]"
            is Cancel -> "Cancel"
            Loading -> "Loading"
        }
    }

    fun getOrNull(): R? {
        return when {
            succeeded -> data
            else -> null
        }
    }

    fun getErrorMsg(): String {
        return when (this) {
            is Success -> "Result is success without error message"
            is Loading -> "Result is loading without error message"
            is Cancel -> "Result is cancelled"
            is Error -> throwable.stackTraceToString()
        }
    }

    fun exceptionOrNull(): Throwable? {
        return if (this is Error) {
            throwable
        } else {
            null
        }
    }


    fun isSuccess(): Boolean {
        return succeeded
    }

}

/**
 * `true` if [Result] is of type [Success] & holds non-null [Success.data].
 */
val Result<*>.succeeded
    get() = this is Success && data != null

fun <T> Result<T>.successOr(fallback: T): T {
    return (this as? Success<T>)?.data ?: fallback
}

val <T> Result<T>.data: T?
    get() = (this as? Success)?.data

/**
 * Updates value of [MutableStateFlow] if [Result] is of type [Success]
 */
inline fun <reified T> Result<T>.updateOnSuccess(stateFlow: MutableStateFlow<T>) {
    if (this is Success) {
        stateFlow.value = data
    }
}
