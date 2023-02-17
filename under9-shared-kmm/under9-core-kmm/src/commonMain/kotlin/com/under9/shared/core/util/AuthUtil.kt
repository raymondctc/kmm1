package com.under9.shared.core.util

import com.soywiz.krypto.sha1
import io.github.aakira.napier.Napier
import io.ktor.utils.io.charsets.*
import io.ktor.utils.io.core.*
import kotlin.math.min
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object AuthUtil {

    fun getAppSignature(timestamp: Long, packageName: String, deviceId: String): String {
        val signString = "*${timestamp}_._${packageName}._.${deviceId}9GAG"
        val sha1 = signString.toByteArray(Charsets.UTF_8).sha1()
        return sha1.hex
    }

    fun isUserTokenExpired(expiryTs: Long): Boolean {
        val now = PlatformUtils.currentTimeMillis / 1000
        val offset: Long = 30
        val isExpired: Boolean = now + offset > expiryTs
        Napier.d("isUserTokenExpired() now=" + now + ", tokenExpiryTs=" + expiryTs + ", diff=" + (expiryTs - now) + ", isExpired=" + isExpired)
        return isExpired
    }

    fun isUserTokenExpiringSoon(expiryTs: Long, secondTillExpiry: Long): Boolean {
        val now = PlatformUtils.currentTimeMillis / 1000
        val secondTillExpire = expiryTs - now

        // use three day to determinant to use new long live token or not

//        return secondTillExpire < 86400 * 3

        // use three day to determinant to use new long live token or not
        return if (secondTillExpire < 86400 * 3) {
            // Consider token as expiring soon if the time between now and expected expiry timestamp is less than 1/3 of token life span (i.e. secondsTillExpiry given by API)
            val isExpiringSoon = now < expiryTs && (now + (secondTillExpiry / 3) > expiryTs)
            isExpiringSoon
        } else {
            val isExpiringSoon = min((86400 * 3).toLong(), (expiryTs - now) / 4) < 86400 * 3

            isExpiringSoon
        }
    }
}