package com.iprism.adbotsuser.data.models.redeemrequest

import com.google.gson.annotations.SerializedName

data class RedeemRequest(

	@field:SerializedName("amount")
	val amount: String,

	@field:SerializedName("user_id")
	val userId: Int,

	@field:SerializedName("auth_token")
	val authToken: String
)
