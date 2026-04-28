package com.iprism.adbotsuser.data.repositories

import com.iprism.adbotsuser.data.models.wallethistory.RedeemHistoryRequest
import com.iprism.adbotsuser.data.remote.HealthDrinksService
import javax.inject.Inject

class RedeemRepository @Inject constructor(private val apiService : HealthDrinksService) {

    suspend fun fetchWalletHistory(request: RedeemHistoryRequest) = apiService.fetchRedeemHistory(request)
}