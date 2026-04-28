package com.iprism.adbotsuser.data.models.userdetails

import com.google.gson.annotations.SerializedName

data class UserDetailsApiResponse(

	@field:SerializedName("response")
	val response: Response,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Boolean
)

data class Response(

	@field:SerializedName("earned_money")
	val earnedMoney: String,

	@field:SerializedName("location")
	val location: String,

	@field:SerializedName("status")
	val status: String
)
