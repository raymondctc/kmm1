package com.ninegag.app.shared.domain.interest

import com.ninegag.app.shared.data.interest.InterestListRepository
import com.ninegag.app.shared.data.interest.model.InterestListModel
import com.under9.shared.core.FlowUseCase
import com.under9.shared.core.result.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class FetchInterestListUseCase(
    private val interestListRepository: InterestListRepository,
    ioDispatcher: CoroutineDispatcher
): FlowUseCase<FetchInterestListUseCase.Param, InterestListModel>(ioDispatcher) {

    override fun execute(parameters: Param): Flow<Result<InterestListModel>> {
        return interestListRepository.getInterestList()
    }

    class Param
}
