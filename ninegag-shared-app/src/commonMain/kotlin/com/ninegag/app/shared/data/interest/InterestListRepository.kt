package com.ninegag.app.shared.data.interest

import com.ninegag.app.shared.data.interest.model.Interest
import com.ninegag.app.shared.data.interest.model.InterestListModel
import com.under9.shared.core.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface InterestListRepository {
    fun getInterestList(): Flow<Result<InterestListModel>>
}

class InterestRepositoryImpl(
    private val remoteInterestDataSource: RemoteInterestDataSource,
    private val localInterestDataSource: LocalInterestDataSource
): InterestListRepository {
    override fun getInterestList(): Flow<Result<InterestListModel>> {
        return flow {
            val result = mutableListOf<Interest>()
            val interestListResult = localInterestDataSource.getInterestList()
            val interestListGetResult = interestListResult.getOrNull()!!
            interestListGetResult.forEachIndexed { index, item ->
                result.add(
                    Interest(
                        id = index,
                        name = item.name,
                        listType = item.list_type,
                        imageUrl = item.webp_url.ifEmpty {
                            item.image_url
                        }
                    )
                )
            }
            emit(Result.Success(InterestListModel(result)))

            val remoteResult = remoteInterestDataSource.getInterestList()
            val remoteGetResult = remoteResult.getOrNull()
            if (remoteResult.isSuccess()) {
                val remoteInterestList = mutableListOf<Interest>()
                localInterestDataSource.addInterestItems(remoteGetResult!!.homeInterests)
                remoteGetResult.homeInterests.forEachIndexed { index, item ->
                    remoteInterestList.add(
                        Interest(
                            id = index,
                            name = item.name,
                            listType = item.listType,
                            imageUrl = item.webpUrl.ifEmpty {
                                item.imageUrl
                            }
                        )
                    )
                }
                emit(Result.Success(InterestListModel(remoteInterestList)))
            }
        }
    }
}