package com.ninegag.app.shared.config

abstract class BaseAdsConfig {
    abstract val adTagListViewHB: String
    abstract val adTagBottomAdhesionBannerHB: String
    abstract val adTagCommentListBannerHB320x50: String
    abstract val adTagCommentListBannerHB300x250: String
    abstract val openAppAdId: String
}

object StgIosAdsConfig : BaseAdsConfig() {
    override val adTagListViewHB: String = "/16921351/9GAG_iOS/9gag-iOS-ListView-Banner-HB"
    override val adTagBottomAdhesionBannerHB: String = "/16921351/9GAG_iOS/9gag-iOS-BottomAdhesion-HB"
    override val adTagCommentListBannerHB320x50: String = "/16921351/9GAG_iOS/9gag-iOS-AboveComment-HB"
    override val adTagCommentListBannerHB300x250: String = "/16921351/9GAG_iOS/9gag-iOS-AboveComment-HB-300x250"
    override val openAppAdId: String = "/6499/example/app-open"
}
object StgAndroidAdsConfig : BaseAdsConfig() {
    override val adTagListViewHB: String = "/16921351/9GAG_Android/9gag-Android-ListView-Banner-HB"
    override val adTagBottomAdhesionBannerHB: String = "/16921351/9GAG_Android/9gag-Android-BottomAdhesion-HB"
    override val adTagCommentListBannerHB320x50: String = "/16921351/9gag-Android-AboveComment-320x50"
    override val adTagCommentListBannerHB300x250: String = "/16921351/9GAG_Android/9gag-Android-AboveComment-HB-300x250"
    override val openAppAdId: String = "ca-app-pub-3940256099942544/3419835294"
}

object ReleaseIosAdsConfig : BaseAdsConfig() {
    override val adTagListViewHB: String = "/16921351/9GAG_iOS/9gag-iOS-ListView-Banner-HB"
    override val adTagBottomAdhesionBannerHB: String = "/16921351/9GAG_iOS/9gag-iOS-BottomAdhesion-HB"
    override val adTagCommentListBannerHB320x50: String = "/16921351/9GAG_iOS/9gag-iOS-AboveComment-HB"
    override val adTagCommentListBannerHB300x250: String = "/16921351/9GAG_iOS/9gag-iOS-AboveComment-HB-300x250"
    override val openAppAdId: String = "/16921351/9GAG_iOS/9gag-iOS-AppOpen-HeaderBidding"
}
object ReleaseAndroidAdsConfig : BaseAdsConfig() {
    override val adTagListViewHB: String = "/16921351/9GAG_Android/9gag-Android-ListView-Banner-HB"
    override val adTagBottomAdhesionBannerHB: String = "/16921351/9GAG_Android/9gag-Android-BottomAdhesion-HB"
    override val adTagCommentListBannerHB320x50: String = "/16921351/9gag-Android-AboveComment-320x50"
    override val adTagCommentListBannerHB300x250: String = "/16921351/9GAG_Android/9gag-Android-AboveComment-HB-300x250"
    override val openAppAdId: String = "/16921351/9GAG_Android/9gag-Android-AppOpen-HeaderBidding"
}