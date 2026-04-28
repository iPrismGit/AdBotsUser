package com.iprism.adbotsuser.data.remote

import com.iprism.adbotsuser.data.models.login.LoginApiResponse
import com.iprism.adbotsuser.data.models.login.LoginRequest
import com.iprism.adbotsuser.data.models.promotiondetails.PromotionDetailsApiResponse
import com.iprism.adbotsuser.data.models.promotiondetails.PromotionDetailsRequest
import com.iprism.adbotsuser.data.models.report.ReportApiResponse
import com.iprism.adbotsuser.data.models.report.ReportRequest
import com.iprism.adbotsuser.data.models.promotions.PromotionsApiResponse
import com.iprism.adbotsuser.data.models.promotions.PromotionsRequest
import com.iprism.adbotsuser.data.models.userdetails.UserDetailsApiResponse
import com.iprism.adbotsuser.data.models.userdetails.UserDetailsRequest
import com.iprism.adbotsuser.data.models.wallethistory.RedeemHistoryApiResponse
import com.iprism.adbotsuser.data.models.wallethistory.RedeemHistoryRequest
import com.iprism.adbotsuser.utils.Constants
import retrofit2.http.Body
import retrofit2.http.POST

interface HealthDrinksService {

    @POST(Constants.LOGIN_ENDPOINT)
    suspend fun login(@Body req : LoginRequest) : LoginApiResponse

    @POST(Constants.PROMOTIONS_ENDPOINT)
    suspend fun fetchPromotions(@Body req : PromotionsRequest) : PromotionsApiResponse

    @POST(Constants.PROMOTION_DETAILS_ENDPOINT)
    suspend fun fetchPromotionDetails(@Body req : PromotionDetailsRequest) : PromotionDetailsApiResponse

    @POST(Constants.USER_REPORT_ENDPOINT)
    suspend fun userReport(@Body req : ReportRequest) : ReportApiResponse

    @POST(Constants.REDEEM_HISTORY_ENDPOINT)
    suspend fun fetchRedeemHistory(@Body request : RedeemHistoryRequest) : RedeemHistoryApiResponse

    @POST(Constants.USER_DETAILS_ENDPOINT)
    suspend fun fetchUserDetails(@Body request : UserDetailsRequest) : UserDetailsApiResponse
}