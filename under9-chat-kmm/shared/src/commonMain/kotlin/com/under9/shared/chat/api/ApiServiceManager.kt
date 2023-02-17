package com.under9.shared.chat.api

import com.under9.shared.chat.api.basemodel.ApiBaseResponse
import com.under9.shared.chat.api.model.*
import com.under9.shared.chat.api.model.ApiLoginResponse
import com.under9.shared.core.result.Result
import com.under9.shared.chat.util.Global
import com.under9.shared.chat.util.RLogMessage
import com.under9.shared.core.util.PlatformUtils
import com.under9.shared.internal.RLogToNapierFormatHelper
import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object ApiServiceManager {

    lateinit var client: HttpClient
    var API_BASE_URL = ""
        private set

    var isUsingStaging = false
    set(value) {
        API_BASE_URL = if (value) {
            Global.CMTY_STAGING_BASE_URL + "/v2/hey/"
        } else {
            Global.CMTY_PRODUCTION_BASE_URL + "/v2/hey/"
        }
        field = value
    }

    suspend fun loginAuthHey(gagHeyUserAccessToken: String): Result<ApiLoginResponse> {
        try {
            Napier.d(
                "api context ${PlatformUtils.currentThreadInfo}",
                tag = "ApiServiceManager"
            )

            val response = client.submitForm(
                url = API_BASE_URL + "authenticate",
                formParameters =  Parameters.build {
                    append("userAccessToken", gagHeyUserAccessToken)
                }
            ).body<ApiLoginResponse>()

            return checkAndReturnResult(response)
        } catch (e: Exception) {
            Napier.e("ApiServiceManager error", e)
            return Result.Error(e)
        }
    }


    suspend fun getRandomHeyFeed(filterType: String): Result<ApiHeyFeedResponse> {
        return try {
            val response = client.get(API_BASE_URL + "feed") {
                parameter("type", filterType)
            }.body<ApiHeyFeedResponse>()

            checkAndReturnResult(response)
        } catch (e: Exception) {
            Napier.e("error=", e)
            Result.Error(e)
        }
    }

    suspend fun getHometownCountryFeed(): Result<ApiHeyFeedResponse> {
        return try {
            val response = client.get(API_BASE_URL + "feed").body<ApiHeyFeedResponse>()

            checkAndReturnResult(response)
        }catch (e: Exception) {
            Napier.e("error=", e)
            Result.Error(e)
        }
    }

    suspend fun setHometown(countryCode: String): Result<ApiHeyHometown> {
        return try {
            val response = client.submitForm(
                url = API_BASE_URL + "hometown",
                formParameters =  Parameters.build {
                    append("country", countryCode)
                }
            ).body<ApiHeyHometown>()

            checkAndReturnResult(response)
        } catch (e: Exception) {
            Napier.e("error=", e)
            Result.Error(e)
        }
    }

    @Deprecated("Clean Up")
    suspend fun setHeyGender(gender: String): Result<ApiHeyGender> {
        return try {
            val response = client.submitForm(
                url = API_BASE_URL + "gender",
                formParameters =  Parameters.build {
                    append("gender", gender)
                }
            ).body<ApiHeyGender>()

            checkAndReturnResult(response)
        } catch (e: Exception) {
            Napier.e("error=", e)
            Result.Error(e)
        }
    }

    suspend fun updateHeyStatus(msg: String): Result<ApiHeyStatusResponse> {
        return try {
            val response = client.submitForm(
                url = API_BASE_URL + "status",
                formParameters =  Parameters.build {
                    append("title", msg)
                }
            ).body<ApiHeyStatusResponse>()

            checkAndReturnResult(response)
        } catch (e: Exception) {
            Napier.e("error=", e)
            Result.Error(e)
        }
    }

    suspend fun getLatestHeyStatus(): Result<ApiHeyStatusResponse> {
        return try {
            val response = client.get(API_BASE_URL + "status").body<ApiHeyStatusResponse>()

            checkAndReturnResult(response)
        } catch (e: Exception) {
            Napier.e("error=", e)
            Result.Error(e)
        }
    }

    suspend fun sendHeyChatRequest(statusId: String): Result<ApiHeyChatRequest> {
        return try {
            val response = client.submitForm(
                url = API_BASE_URL + "chat-request",
                formParameters =  Parameters.build {
                    append("statusId", statusId)
                }
            ).body<ApiHeyChatRequest>()

            checkAndReturnResult(response)
        } catch (e: Exception) {
            Napier.e("error=", e)
            Result.Error(e)
        }
    }

    suspend fun acceptChatRequest(requestId: String): Result<ApiHeyChatAccept> {
        return try {
            val response = client.submitForm(
                url = API_BASE_URL + "chat-accept",
                formParameters =  Parameters.build {
                    append("requestId", requestId)
                }
            ).body<ApiHeyChatAccept>()

            if (response.success()) {
                checkAndReturnResult(response)
            } else {
                Result.Error(IllegalStateException(response.errorMessage))
            }
        } catch (e: Exception) {
            Napier.e("error=", e)
            Result.Error(e)
        }
    }

    suspend fun leaveChat(chatId: String): Result<ApiBaseResponse> {
        return try {
            val response = client.submitForm(
                url = API_BASE_URL + "chat-leave",
                formParameters =  Parameters.build {
                    append("chatId", chatId)
                }
            ).body<ApiBaseResponse>()

            checkAndReturnResult(response)

        } catch (e: Exception) {
            Napier.e("error=", e)
            Result.Error(e)
        }
    }

    suspend fun getChatToken(): Result<ApiHeyChatToken> {
        return try {
            val response = client.get(API_BASE_URL + "chat-token").body<ApiHeyChatToken>()

            checkAndReturnResult(response)

        } catch (e: Exception) {
            Napier.e("error=", e)
            Result.Error(e)
        }
    }

    suspend fun renewToken(): Result<ApiLoginResponse> {
        return try {
            Napier.i(
                RLogToNapierFormatHelper.formatMessage(RLogMessage.Key.API_RENEW_TOKEN, "renewToken=${PlatformUtils.currentTimeMillis}"),
                tag = RLogMessage.Category.API
            )
            val response = client.get(API_BASE_URL + "authenticate/loginMethod/renew").body<ApiLoginResponse>()
            checkAndReturnResult(response)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    // Subscribe FCM notification, for clients can't subscribe topic
    suspend fun subscribeFcmNotification(fcmToken: String): Result<ApiBaseResponse> {
        return try {
            val response = client.submitForm(
                url = API_BASE_URL + "fcm-token",
                formParameters =  Parameters.build {
                    append("fcmToken", fcmToken)
                }
            ).body<ApiBaseResponse>()

            return checkAndReturnResult(response)
        } catch (e: Exception) {
            Napier.e("error=", e)
            Result.Error(e)
        }
    }

    // Unsubscribe FCM notification, for clients can't unsubscribe topic
    suspend fun unsubscribeFcmNotification(fcmToken: String): Result<ApiBaseResponse> {
        return try {
            val response = client.delete {
                chatApiUrl("fcm-token")
                parameter("fcmToken", fcmToken)
            }.body<ApiBaseResponse>()
            return checkAndReturnResult(response)
        } catch (e: Exception) {
            Napier.e("error=", e)
            Result.Error(e)
        }
    }

    private fun <T: ApiBaseResponse> checkAndReturnResult(response: T): Result<T> {
        return if (response.success()) {
            Result.Success(response)
        } else {
            Result.Error(IllegalStateException("error code=${response.meta?.errorCode}\nmessage=${ response.meta?.errorMessage}"))
        }
    }

    internal fun HttpRequestBuilder.chatApiUrl(path: String) {
        url {
            takeFrom(API_BASE_URL)
            encodedPath = path
        }
    }
}