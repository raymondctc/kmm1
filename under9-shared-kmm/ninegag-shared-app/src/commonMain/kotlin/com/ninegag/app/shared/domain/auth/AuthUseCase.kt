package com.ninegag.app.shared.domain.auth

import com.ninegag.app.shared.data.auth.RemoteAuthDataSource
import com.ninegag.app.shared.data.auth.model.AccountSession
import com.ninegag.app.shared.data.user.LocalUserDatasource
import com.ninegag.app.shared.data.user.model.AppUser
import com.under9.shared.core.UseCase
import kotlinx.coroutines.CoroutineDispatcher

class AuthUseCase(
    private val remoteAuthDataSource: RemoteAuthDataSource,
    private val localUserDatasource: LocalUserDatasource,
    ioDispatcher: CoroutineDispatcher
) : UseCase<AuthUseCase.Param, AccountSession>(ioDispatcher) {

    override suspend fun execute(parameters: Param): AccountSession {
        return when (parameters) {
            is Guest -> {
                val result = remoteAuthDataSource.loginGuest()
                if (result.isSuccess()) {
                    val authResponse = result.getOrNull()!!
                    val appToken = AccountSession.AppToken(
                        userToken = authResponse.data.userToken!!,
                        expiryTs = authResponse.data.tokenExpiry!!,
                        secondsTillExpiry = authResponse.data.secondsTillExpiry!!
                    )
                    val session = AccountSession(appToken, null, null, null)
                    localUserDatasource.tmp_cacheAccountSession(session)
                    session
                } else {
                    throw result.exceptionOrNull() ?: RuntimeException("Unknown exception")
                }
            }

            is LoginName -> {
                // TODO: Clean up this temp method
                localUserDatasource.resetUserToken()

                val result = remoteAuthDataSource.loginBy9Gag(loginName = parameters.loginName, password = parameters.password)
                if (result.isSuccess()) {
                    val authResponse = result.getOrNull()!!
                    val appToken = AccountSession.AppToken(
                        userToken = authResponse.data.userToken!!,
                        expiryTs = authResponse.data.tokenExpiry!!,
                        secondsTillExpiry = authResponse.data.secondsTillExpiry!!
                    )
                    val commentToken = authResponse.commentAuth?.let {
                        AccountSession.CommentToken(
                            type = it.type,
                            authToken = it.authHash,
                            expiryTs = it.expiryTs
                        )
                    }
                    val notifToken = authResponse.notifAuth?.let {
                        AccountSession.NotifToken(
                            readStateParams = it.readStateParams
                        )
                    }
                    val user = AppUser(
                        loginName = authResponse.data.user!!.loginName,
                        accountId = authResponse.data.user.accountId,
                        userId = authResponse.data.user.userId
                    )
                    val session = AccountSession(appToken, commentToken, notifToken, user)
                    localUserDatasource.tmp_cacheAccountSession(session)

                    session
                } else {
                    throw result.exceptionOrNull() ?: RuntimeException("Unknown exception")
                }
            }
            else -> throw IllegalArgumentException("Unknown type")
        }
    }

    sealed class Param
    data class LoginName(val loginName: String, val password: String) : Param()
    data class SocialToken(val socialToken: String) : Param()
    object Guest : Param()
}