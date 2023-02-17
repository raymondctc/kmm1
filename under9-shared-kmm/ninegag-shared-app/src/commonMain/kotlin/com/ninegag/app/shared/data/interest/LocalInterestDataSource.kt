package com.ninegag.app.shared.data.interest

import com.under9.shared.core.result.Result
import com.ninegag.app.shared.db.InterestEntity
import com.ninegag.app.shared.db.SharedNineGagDatabase
import com.ninegag.app.shared.infra.remote.interest.model.ApiInterest

interface LocalInterestDataSource {
    fun addInterestItems(interest: List<ApiInterest>)
    fun getInterestList(): Result<List<InterestEntity>>
}

class LocalInterestDataSourceImpl(
    private val database: SharedNineGagDatabase
): LocalInterestDataSource {

    override fun addInterestItems(interest: List<ApiInterest>) {
        database.interestListQueries.transaction {
            database.interestListQueries.clearList()
            interest.forEach {
                database.interestListQueries.addToList(
                    it.name, it.listType, it.imageUrl, it.webpUrl
                )
            }
        }
    }

    override fun getInterestList(): Result<List<InterestEntity>> {
        val r = database.interestListQueries.getList().executeAsList()
        return Result.Success(r)
    }

}