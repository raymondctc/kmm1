package com.ninegag.app.shared.config

internal expect val CurrentPlatform : PlatformEnum

internal enum class PlatformEnum {
    IOS, ANDROID
}