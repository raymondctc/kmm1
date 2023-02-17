package com.ninegag.app.shared.util

import com.ninegag.app.shared.infra.remote.base.ApiBaseResponse
import com.under9.shared.core.result.Result
import io.github.aakira.napier.Napier

internal fun <T: ApiBaseResponse> checkAndReturnResult(response: T): Result<T> {
    return if (response.success()) {
        Result.Success(response)
    } else {
        Result.Error(IllegalStateException("error code=${response.meta?.errorCode}\nmessage=${ response.meta?.errorMessage}"))
    }
}

inline fun <T: ApiBaseResponse> runWithSafeApiResult(apiCall: () -> T): com.under9.shared.core.result.Result<T> {
    return try {
        val result = apiCall()
        Napier.d("esult=${result.meta}")
        if (result.success()) {
            Result.Success(result)
        } else {
            Result.Error(RuntimeException("ApiError, resp=$result"))
        }
    } catch (e: Exception) {
        Napier.e("Execution error, e.message=${e.message}", e)
        return com.under9.shared.core.result.Result.Error(e)
    }
}