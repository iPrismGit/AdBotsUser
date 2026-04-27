package com.iprism.adbotsuser.data.models.promotions

import com.google.gson.annotations.SerializedName

data class PromotionsApiResponse(

	@field:SerializedName("response")
	val response: Response,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Boolean
)

data class TotalPagesItem(

	@field:SerializedName("page")
	val page: Int
)

data class PromotionsItem(

	@field:SerializedName("end_date")
	val endDate: String,

	@field:SerializedName("updated_on")
	val updatedOn: Any,

	@field:SerializedName("earned_amount")
	val earnedAmount: String,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("play_time")
	val playTime: String,

	@field:SerializedName("user_id")
	val userId: String,

	@field:SerializedName("bussiness_name")
	val bussinessName: String,

	@field:SerializedName("created_on")
	val createdOn: Any,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("price_per_minute")
	val pricePerMinute: String,

	@field:SerializedName("start_date")
	val startDate: String,

	@field:SerializedName("total_days")
	val totalDays: String
)

data class Response(

	@field:SerializedName("promotions")
	val promotions: List<PromotionsItem>,

	@field:SerializedName("pagination")
	val pagination: Pagination
)

data class Pagination(

	@field:SerializedName("limit")
	val limit: Int,

	@field:SerializedName("total_pages")
	val totalPages: List<TotalPagesItem>,

	@field:SerializedName("current_page")
	val currentPage: Int
)
