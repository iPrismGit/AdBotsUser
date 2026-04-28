package com.iprism.adbotsuser.data.models.userdetails

import com.google.gson.annotations.SerializedName

data class UserDetailsRequest(

	@field:SerializedName("user_id")
	val userId: Int,

	@field:SerializedName("auth_token")
	val authToken: String
)
