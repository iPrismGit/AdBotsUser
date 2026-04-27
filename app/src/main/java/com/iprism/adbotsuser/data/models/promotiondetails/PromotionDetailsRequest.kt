package com.iprism.adbotsuser.data.models.promotiondetails

import com.google.gson.annotations.SerializedName

data class PromotionDetailsRequest(

	@field:SerializedName("user_id")
	val userId: Int,

	@field:SerializedName("promotion_id")
	val promotionId: String,

	@field:SerializedName("auth_token")
	val authToken: String
)
