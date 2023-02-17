package com.ninegag.app.shared.di.module

import com.ninegag.app.shared.domain.campaign.GetCampaignsUseCase
import com.ninegag.app.shared.domain.campaign.UpdateCampaignsUseCase
import com.ninegag.app.shared.domain.GagHttpHeaderValueManager
import com.ninegag.app.shared.domain.UpdateUserSettingsOneShotUseCase
import com.ninegag.app.shared.domain.auth.AuthUseCase
import com.ninegag.app.shared.domain.post.DeletePostUseCase
import com.ninegag.app.shared.domain.post.SubmitPostWithLinkUseCase
import com.ninegag.app.shared.domain.tag.*
import com.ninegag.app.shared.domain.user.*
import com.ninegag.app.shared.domain.tag.FetchRelatedTagUseCase
import com.ninegag.app.shared.domain.interest.FetchInterestListUseCase
import com.ninegag.app.shared.domain.post.FetchRemoteRelatedPostUseCase
import com.russhwolf.settings.Settings
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val useCaseModule: Module = module {
    val nameDispatcherIO = "DispatcherIO"
    factory { AuthUseCase(get(), get(), get(named(nameDispatcherIO))) }
    factory { UpdateUserSettingsOneShotUseCase(get(), get(named(nameDispatcherIO))) }
    factory { BlockPostOneShotUseCase(get(), get(named(nameDispatcherIO))) }
    factory { ManageBlockUserOneShotUseCase(get(), get(named(nameDispatcherIO))) }
    factory { CheckUserBlockedOneShotUseCase(get()) }
    factory { CheckHidePostOneShotUseCase(get()) }
    factory { ClearBlockedPostOneShotUseCase(get()) }
    factory { ClearBlockedUserOneShotUseCase(get()) }
    factory { CacheUserInfoOneShotUseCase(get()) }
    factory { DeletePostUseCase(get(),get(named(nameDispatcherIO))) }
    factory { UpdateCampaignsUseCase(get()) }
    factory { GetCampaignsUseCase(get()) }
    factory { SubmitPostWithLinkUseCase(get(), get(named(nameDispatcherIO))) }
    factory { FetchNavTagListUseCase(get(), get(named(nameDispatcherIO))) }
    factory { FetchTrendingTagListUseCase(get(), get(named(nameDispatcherIO))) }
    factory { FetchHistoryTagListUseCase(get(), get(named(nameDispatcherIO))) }
    factory { StoreHistoryTagUseCase(get(), get(named(nameDispatcherIO))) }
    factory { FetchInterestListUseCase(get(), get(named(nameDispatcherIO))) }
    factory { UpdateFavHiddenRecentStatusUseCase(get(), get(named(nameDispatcherIO))) }
    factory { ClearRecentItemsUseCase(get(), get(named(nameDispatcherIO))) }
    factory { FetchRelatedTagUseCase(get(), get(named(nameDispatcherIO))) }
    factory { FetchRemoteRelatedPostUseCase(get(), get(named(nameDispatcherIO))) }
}

/**
 * To be used by iOS
 * Suggested by https://insert-koin.io/docs/reference/koin-mp/kmp/
 */
class UseCaseHelper : KoinComponent {
    val authUseCase: AuthUseCase by inject()
    val blockUserUseCase: BlockPostOneShotUseCase by inject()
    val fetchNavTagListUseCase: FetchNavTagListUseCase by inject()
    val fetchInterestListUseCase: FetchInterestListUseCase by inject()
    val updateFavHiddenRecentStatusUseCase: UpdateFavHiddenRecentStatusUseCase by inject()
    val clearRecentStatusUseCase: ClearRecentItemsUseCase by inject()

    private val settings: Settings by inject()

    fun tmp_cacheIosUserToken(userToken: String) {
        settings.putString(GagHttpHeaderValueManager.KEY_NINE_GAG_TOKEN, userToken)
    }
}
