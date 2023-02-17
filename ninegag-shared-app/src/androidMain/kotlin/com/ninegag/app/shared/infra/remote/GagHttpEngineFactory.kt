package com.ninegag.app.shared.infra.remote

import android.content.Context
import com.google.net.cronet.okhttptransport.CronetInterceptor
import com.ninegag.app.shared.AppContext
import com.ninegag.app.shared.config.UserAgentConfig
import com.ninegag.app.shared.domain.GagHttpHeaderValueManager
import io.github.aakira.napier.Napier
import io.ktor.client.engine.*
import io.ktor.client.engine.okhttp.*
import io.ktor.utils.io.errors.*
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody
import okio.BufferedSink
import org.chromium.net.CronetEngine

actual class GagHttpEngineFactory actual constructor(
   private val httpHeaderValueManager: GagHttpHeaderValueManager,
   private val userAgentConfig: UserAgentConfig,
   private val appContext: AppContext<*>
) {

    actual fun createHttpEngine(): HttpClientEngineFactory<HttpClientEngineConfig> = OkHttp
    actual fun setEngineConfig(config: HttpClientEngineConfig) {
        (config as OkHttpConfig).apply {
            addInterceptor(createHeaderInterceptor())
            addNetworkInterceptor(Interceptor { chain ->
                val request = chain.request()
                try {
                    return@Interceptor chain.proceed(request)
                } catch (e: IOException) {
                    throw e
                }
            })

            if (userAgentConfig.enableQUICProvider()) {
                try {
                    Napier.i("Creating QUIC for KMM")
                    val engine = CronetEngine.Builder(appContext.getAppContext() as Context).build()
                    val cronetInterceptor = CronetInterceptor.newBuilder(engine).build()
                    
                    // Workaround to skip CronetInterceptor duplicated Content-Type issue
                    // https://github.com/google/cronet-transport-for-okhttp/issues/9
                    // TODO: Remove this interceptor once a new cronet release is published
                    addInterceptor(createCheckContentTypeInterceptor())
                    addInterceptor(cronetInterceptor)
                } catch (e: RuntimeException) {
                    Napier.w("setEngineConfig", e)
                }
            }
        }
    }

    private fun createHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            val request = buildRequest(original)

            val response = chain.proceed(request)
            response
        }
    }

    private fun buildRequest(original: Request): Request {
        val token = httpHeaderValueManager.getToken()
        val headerRequestDataModel = httpHeaderValueManager.createRequestHeader()
        val builder = original.newBuilder()
        with(headerRequestDataModel) {
            builder
                .addHeader(com.under9.shared.infra.network.model.MainApiHeader.HEADER_TOKEN, token)
                .addHeader(com.under9.shared.infra.network.model.MainApiHeader.HEADER_TIMESTAMP, timestamp)
                .addHeader(com.under9.shared.infra.network.model.MainApiHeader.HEADER_APP_ID, packageName)
                .addHeader(com.under9.shared.infra.network.model.MainApiHeader.HEADER_DEVICE_UUID, deviceUUID)
                .addHeader(com.under9.shared.infra.network.model.MainApiHeader.HEADER_REQUEST_SIGNATURE, requestSignature)
                .addHeader(com.under9.shared.infra.network.model.MainApiHeader.HEADER_X_PACKAGE_ID, packageName)
                .addHeader(com.under9.shared.infra.network.model.MainApiHeader.HEADER_X_PACKAGE_VERSION, packageVersion)
                .addHeader(com.under9.shared.infra.network.model.MainApiHeader.HEADER_X_PACKAGE_DEVICE_ID, deviceUUID)
                .addHeader(com.under9.shared.infra.network.model.MainApiHeader.HEADER_DEVICE_TYPE, deviceType)
        }

        return builder.build()
    }

    private fun createCheckContentTypeInterceptor(): Interceptor {
        return Interceptor{ chain ->
            val original = chain.request()
            val builder = original.newBuilder()
            original.body?.let {
                builder.method(original.method, buildPostRequestWithContentType(it, original.header("Content-Type")))
                builder.removeHeader("Content-Type")
            }
            chain.proceed(builder.build())
        }
    }

    private fun buildPostRequestWithContentType(original: RequestBody, contentType: String?): RequestBody {
        return object : RequestBody() {
            override fun contentType(): MediaType? {
                // Fallback on original header contentType
                return original.contentType() ?: contentType?.toMediaType()
            }

            override fun writeTo(sink: BufferedSink) {
                original.writeTo(sink)
            }
        }
    }
}