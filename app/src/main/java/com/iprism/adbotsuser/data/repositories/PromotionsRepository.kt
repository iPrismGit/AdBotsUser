package com.iprism.adbotsuser.data.repositories

import android.util.Log
import com.iprism.adbotsuser.data.models.login.LoginApiResponse
import com.iprism.adbotsuser.data.models.login.LoginRequest
import com.iprism.adbotsuser.data.models.promotions.PromotionsApiResponse
import com.iprism.adbotsuser.data.models.promotions.PromotionsRequest
import com.iprism.adbotsuser.data.remote.HealthDrinksService
import com.iprism.adbotsuser.utils.DataStoreManager
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import kotlin.toString

class PromotionsRepository @Inject constructor(
    private val apiService: HealthDrinksService, private val dataStoreManager: DataStoreManager
) {
    suspend fun fetchPromotions(page: Int): PromotionsApiResponse {
        val user = dataStoreManager.userDetails.first()
        val request = PromotionsRequest(
            userId = user.userId?.toInt() ?: 0,
            authToken = user.token ?: "",
            page = page.toString()
        )
        Log.d("requestLoading", request.toString())
        return apiService.fetchPromotions(request)
    }
}