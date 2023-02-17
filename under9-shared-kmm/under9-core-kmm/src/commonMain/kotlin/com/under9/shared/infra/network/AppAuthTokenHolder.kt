package com.under9.shared.infra.network

import co.touchlab.stately.concurrency.AtomicReference
import kotlinx.coroutines.CompletableDeferred

/**
 * A copy of [io.ktor.client.features.auth.providers.AuthTokenHolder]
 * Since this class is internal, we have to implement it to avoid token suspend
 * function being invoked multiple times from request
 */
class AppAuthTokenHolder<T>(
    private val loadTokens: suspend () -> T?
) {
    private val cachedBearerTokens: AtomicReference<CompletableDeferred<T?>> = AtomicReference(
        CompletableDeferred<T?>().apply {
            complete(null)
        }
    )

    internal suspend fun clearToken() {
        cachedBearerTokens.set(CompletableDeferred<T?>().apply { complete(null) })
    }

    internal suspend fun loadToken(): T? {
        val cachedToken = cachedBearerTokens.get().await()
        if (cachedToken != null) return cachedToken

        return setToken(loadTokens)
    }

    internal suspend fun setToken(block: suspend () -> T?): T? {
        val old = cachedBearerTokens.get()
        if (!old.isCompleted) {
            return old.await()
        }

        val deferred = CompletableDeferred<T?>()
        if (!cachedBearerTokens.compareAndSet(old, deferred)) {
            return cachedBearerTokens.get().await()
        }

        try {
            val token = block()
            deferred.complete(token)
            return token
        } catch (cause: Throwable) {
            deferred.completeExceptionally(cause)
            throw cause
        }
    }
}