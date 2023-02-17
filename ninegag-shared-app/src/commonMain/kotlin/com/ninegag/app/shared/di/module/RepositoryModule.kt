package com.ninegag.app.shared.di.module

import com.ninegag.app.shared.data.post.PostRepository
import com.ninegag.app.shared.data.post.PostRepositoryImpl
import com.ninegag.app.shared.data.post.RemotePostDatasource
import com.ninegag.app.shared.data.campaign.CampaignsRepository
import com.ninegag.app.shared.data.campaign.CampaignsRepositoryImpl
import com.ninegag.app.shared.data.campaign.LocalCampaignsDataSourceImpl
import com.ninegag.app.shared.data.campaign.LocalCampaignsDatasource
import com.ninegag.app.shared.data.auth.RemoteAuthDataSource
import com.ninegag.app.shared.data.auth.RemoteAuthDataSourceImpl
import com.ninegag.app.shared.data.interest.*
import com.ninegag.app.shared.data.post.*
import com.ninegag.app.shared.data.post.RemotePostDatasourceImpl
import com.ninegag.app.shared.data.setting.*
import com.ninegag.app.shared.data.tag.*
import com.ninegag.app.shared.data.user.*
import org.koin.dsl.module

internal val repositoryModule = module {
    single<SettingRepository> { SettingRepositoryImpl(get(), get()) }
    single<UserRepository> { UserRepositoryImpl(get(), get()) }
    single<PostRepository> { PostRepositoryImpl(get()) }
    single<CampaignsRepository> { CampaignsRepositoryImpl(get()) }
    single<TagListRepository> { TagListRepositoryImpl(get(), get()) }
    single<InterestListRepository> { InterestRepositoryImpl(get(), get()) }
}

internal val facadeModule = module {
    single<PostFacade> { PostFacadeImpl(get()) }
}

internal val localDatasourceModule = module {
    single<LocalSettingDatasource> {
        LocalSettingDatasourceImpl(get(), get())
    }
    single<LocalUserDatasource> {
        LocalUserDatasourceImpl(get())
    }
    single<LocalCampaignsDatasource> { LocalCampaignsDataSourceImpl(get()) }
    single<LocalTagDataSource> { LocalTagDataSourceImpl(get()) }
    single<LocalInterestDataSource> { LocalInterestDataSourceImpl(get()) }
}

internal val remoteDatasourceModule = module {
    single<RemoteAuthDataSource> {
        RemoteAuthDataSourceImpl(get())
    }
    single<RemoteSettingDatasource> {
        RemoteSettingDatasourceImpl(get())
    }
    single<RemoteUserDatasource> {
        RemoteUserDatasourceImpl(get())
    }
    single<RemotePostDatasource> {
        RemotePostDatasourceImpl(get())
    }
    single<RemoteTagDataSource> {
        RemoteTagDataSourceImpl(get())
    }
    single<RemoteInterestDataSource> {
        RemoteInterestDataSourceImpl(get())
    }
}
