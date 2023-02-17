package com.ninegag.app.shared.config

abstract class BaseAppConfig {
    abstract val appApiEndpoint: String
    abstract val appEnv: String
    abstract val appId: String
    abstract val appPrivacySettingsUrl: String
    abstract val authAppleClientId: String
    abstract val debug: Boolean
    abstract val domain: String
    val policyCopyRightUrl = "https://9gag.com/copyright"
    val policyFaqSensitiveUrl = "https://9gag.com/faq-sensitive"
    val policyPrivacyUrl: String = "https://9gag.com/privacy"
    val policyPrivacyCCPAUrl: String = "https://9gag.com/privacy-california"
    val policyPrivacyCCPAContactUsUrl: String = "https://9gag.com/privacy-contact#form-contactusprivacy"
    val policyReportAdsUrl: String = "https://9gag.com/report-bad-ads"
    val policyReportCommentOrUserSurveyUrl: String = "https://www.surveymonkey.com/r/GSJ3NTF"
    val policyReportTakeDownUrl: String = "https://9gag.com/copyright#takedown-notice"
    val policyRulesUrl: String = "https://9gag.com/rules"
    val policyTermsOfServiceUrl: String = "https://9gag.com/tos"
    val miscFacebookPageUrl: String = "https://facebook.com/9gag"
    val miscInstagramPageUrl: String = "https://www.instagram.com/9gag"
    val miscTwitterPageUrl: String = "https://twitter.com/9gag"
    abstract val miscEAACountries: String
    abstract val storeUrl: String
    abstract val supportEmail: String

    // Sub systems config
    abstract val adsConfig: BaseAdsConfig
    abstract val commentConfig: BaseCommentConfig
    abstract val giphyConfig: BaseGiphyConfig
    abstract val helpshiftConfig: BaseHelpshiftConfig
    abstract val oneTrustConfig: BaseOneTrustConfig
    abstract val mixpanelConfig: BaseMixpanelConfig
    abstract val notificationConfig: BaseNotificationConfig
    abstract val rlogConfig : BaseRLogConfig
}

/**
 * Not to use `expect` intentionally as it involves too many layers of inheritance
 * check commit 69eccfbae9bfcdec0c553b2aa558b919bc28b356 on previous implementation
 */
abstract class BaseStgAppConfig : BaseAppConfig() {
    override val debug: Boolean = true
    override val domain: String = "9jokes.com"
    override val appEnv: String = "Staging"
    override val appApiEndpoint: String = "https://api.9jokes.com"
    override val appPrivacySettingsUrl: String = "https://9jokes.com/settings/privacy"
    override val authAppleClientId: String = "com.9gag.service.applesignin.stg"
    override val miscEAACountries: String = "HK,AT,BE,BG,HR,CY,CZ,DK,EE,FI,FR,DE,GR,HU,IE,IT,LV,LT,LU,MT,NL,PL,PT,RO,SK,SI,ES,SE,GB,IS,LI,NO"
    override val mixpanelConfig: BaseMixpanelConfig = StgMixpanelConfig
    override val notificationConfig: BaseNotificationConfig = StgNotificationConfig
}

abstract class BaseReleaseAppConfig : BaseAppConfig() {
    override val debug: Boolean = false
    override val domain: String = "9gag.com"
    override val appEnv: String = "Release"
    override val appApiEndpoint: String = "https://api.9gag.com"
    override val appPrivacySettingsUrl: String = "https://9gag.com/settings/privacy"
    override val authAppleClientId: String = "com.9gag.service.applesignin"
    override val miscEAACountries: String = "AT,BE,BG,HR,CY,CZ,DK,EE,FI,FR,DE,GR,HU,IE,IT,LV,LT,LU,MT,NL,PL,PT,RO,SK,SI,ES,SE,GB,IS,LI,NO"
    override val mixpanelConfig: BaseMixpanelConfig = ReleaseMixpanelConfig
    override val notificationConfig: BaseNotificationConfig = ReleaseNotificationConfig
}

/**
 * ===================STAGING=====================
 */
object StgIosAppConfig : BaseStgAppConfig() {
    override val appId: String = "com.9gag.ios.mobile.stg"
    override val supportEmail: String = "support+ios@9gag.com"
    override val storeUrl: String = "itms-apps://itunes.apple.com/app/id545551605"
    override val adsConfig: BaseAdsConfig = StgIosAdsConfig
    override val commentConfig: BaseCommentConfig = StgIosCommentConfig
    override val giphyConfig: BaseGiphyConfig = StgIosGiphyConfig
    override val helpshiftConfig: BaseHelpshiftConfig = StgIosHelpshiftConfig
    override val oneTrustConfig: BaseOneTrustConfig = StgIosOneTrustConfig
    override val rlogConfig: BaseRLogConfig = StgIosRLogConfig
}
object StgAndroidAppConfig : BaseStgAppConfig() {
    override val appId: String = "com.ninegag.android.app.jokes"
    override val supportEmail: String = "support+android@9gag.com"
    override val storeUrl: String = "market://details?id=com.ninegag.android.app"
    override val adsConfig: BaseAdsConfig = StgAndroidAdsConfig
    override val commentConfig: BaseCommentConfig = StgAndroidCommentConfig
    override val giphyConfig: BaseGiphyConfig = StgAndroidGiphyConfig
    override val helpshiftConfig: BaseHelpshiftConfig = StgAndroidHelpshiftConfig
    override val oneTrustConfig: BaseOneTrustConfig = StgAndroidOneTrustConfig
    override val rlogConfig: BaseRLogConfig = StgAndroidRLogConfig
}

/**
 * ===================RELEASE=====================
 */
object ReleaseIosAppConfig : BaseReleaseAppConfig() {
    override val appId: String = "com.9gag.ios.mobile"
    override val supportEmail: String = "support+ios@9gag.com"
    override val storeUrl: String = "itms-apps://itunes.apple.com/app/id545551605"
    override val adsConfig: BaseAdsConfig = ReleaseIosAdsConfig
    override val commentConfig: BaseCommentConfig = ReleaseIosCommentConfig
    override val giphyConfig: BaseGiphyConfig = ReleaseIosGiphyConfig
    override val helpshiftConfig: BaseHelpshiftConfig = ReleaseIosHelpshiftConfig
    override val oneTrustConfig: BaseOneTrustConfig = ReleaseIosOneTrustConfig
    override val rlogConfig: BaseRLogConfig = ReleaseIosRLogConfig
}
object ReleaseAndroidAppConfig : BaseReleaseAppConfig() {
    override val appId: String = "com.ninegag.android.app"
    override val supportEmail: String = "support+android@9gag.com"
    override val storeUrl: String = "market://details?id=com.ninegag.android.app"
    override val adsConfig: BaseAdsConfig = ReleaseAndroidAdsConfig
    override val commentConfig: BaseCommentConfig = ReleaseAndroidCommentConfig
    override val giphyConfig: BaseGiphyConfig = ReleaseAndroidGiphyConfig
    override val helpshiftConfig: BaseHelpshiftConfig = ReleaseAndroidHelpshiftConfig
    override val oneTrustConfig: BaseOneTrustConfig = ReleaseAndroidOneTrustConfig
    override val rlogConfig: BaseRLogConfig = ReleaseAndroidRLogConfig
}