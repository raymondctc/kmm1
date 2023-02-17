package com.under9.shared.infra.network

import io.ktor.client.*

expect class PlatformAppTokenClientFactory {
    fun createClient(httpHeaderValueManager: HttpHeaderValueManager): HttpClient
}