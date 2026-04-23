package com.iprism.adbotsuser.data.remote

import com.iprism.adbotsuser.data.models.login.LoginApiResponse
import com.iprism.adbotsuser.data.models.login.LoginRequest
import com.iprism.adbotsuser.utils.Constants
import retrofit2.http.POST

interface HealthDrinksService {

    @POST(Constants.LOGIN_ENDPOINT)
    suspend fun login(req : LoginRequest) : LoginApiResponse
}