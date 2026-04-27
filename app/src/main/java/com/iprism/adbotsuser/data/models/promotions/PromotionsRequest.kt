package com.iprism.adbotsuser.data.models.promotions

import com.google.gson.annotations.SerializedName

data class PromotionsRequest(

	@field:SerializedName("user_id")
	val userId: Int,

	@field:SerializedName("page")
	val page: String,

	@field:SerializedName("auth_token")
	val authToken: String
)
