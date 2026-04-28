package com.iprism.adbotsuser.data.models.wallethistory

import com.google.gson.annotations.SerializedName

data class RedeemHistoryRequest(

	@field:SerializedName("user_id")
	val userId: Int,

	@field:SerializedName("page")
	val page: Int,

	@field:SerializedName("auth_token")
	val authToken: String
)
