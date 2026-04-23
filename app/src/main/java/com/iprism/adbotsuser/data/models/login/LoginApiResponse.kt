package com.iprism.adbotsuser.data.models.login

import com.google.gson.annotations.SerializedName

data class LoginApiResponse(

	@field:SerializedName("response")
	val response: Response,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Boolean
)

data class UserDetails(

	@field:SerializedName("updated_on")
	val updatedOn: String,

	@field:SerializedName("device_status")
	val deviceStatus: String,

	@field:SerializedName("created_on")
	val createdOn: String,

	@field:SerializedName("user_name")
	val userName: String,

	@field:SerializedName("delete_status")
	val deleteStatus: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("mobile")
	val mobile: String,

	@field:SerializedName("cat_id")
	val catId: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("location_id")
	val locationId: String,

	@field:SerializedName("token")
	val token: String,

	@field:SerializedName("status")
	val status: String
)

data class Response(

	@field:SerializedName("user_details")
	val userDetails: UserDetails
)
