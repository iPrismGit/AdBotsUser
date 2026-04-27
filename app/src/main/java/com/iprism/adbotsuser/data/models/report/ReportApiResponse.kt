package com.iprism.adbotsuser.data.models.report

import com.google.gson.annotations.SerializedName

data class ReportApiResponse(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("response")
    val response: Any? = null
)
