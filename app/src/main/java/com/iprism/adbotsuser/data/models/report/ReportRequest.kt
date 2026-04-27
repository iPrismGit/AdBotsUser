package com.iprism.adbotsuser.data.models.report

import com.google.gson.annotations.SerializedName

data class ReportRequest(
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("auth_token")
    val authToken: String,
    @SerializedName("promotion_id")
    val promotionId: String
)
