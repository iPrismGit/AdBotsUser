package com.iprism.adbotsuser.data.repositories

import android.util.Log
import com.iprism.adbotsuser.data.models.User
import com.iprism.adbotsuser.data.models.login.LoginApiResponse
import com.iprism.adbotsuser.data.models.login.LoginRequest
import com.iprism.adbotsuser.data.models.promotiondetails.PromotionDetailsApiResponse
import com.iprism.adbotsuser.data.models.promotiondetails.PromotionDetailsRequest
import com.iprism.adbotsuser.data.models.report.ReportApiResponse
import com.iprism.adbotsuser.data.models.report.ReportRequest
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

    suspend fun getUser(): User {
        return dataStoreManager.userDetails.first()
    }


    suspend fun fetchPromotions(page: Int): PromotionsApiResponse {
        val user = getUser()
        val request = PromotionsRequest(
            userId = user.userId?.toInt() ?: 0,
            authToken = user.token ?: "",
            page = page.toString()
        )
        Log.d("requestLoading", request.toString())
        return apiService.fetchPromotions(request)
    }

    suspend fun fetchPromotionDetails(promotionId: String): PromotionDetailsApiResponse {
        val user = getUser()
        val request = PromotionDetailsRequest(
            userId = user.userId?.toInt() ?: 0,
            authToken = user.token ?: "",
            promotionId = promotionId
        )
        Log.d("requestLoading", request.toString())
        return apiService.fetchPromotionDetails(request)
    }

    suspend fun userReport(promotionId: String): ReportApiResponse {
        val user = getUser()
        val request = ReportRequest(
            userId = user.userId?.toInt() ?: 0,
            authToken = user.token ?: "",
            promotionId = promotionId
        )
        Log.d("requestLoading", request.toString())
        return apiService.userReport(request)
    }
}