package com.iprism.adbotsuser.data.repositories

import com.iprism.adbotsuser.data.models.login.LoginRequest
import com.iprism.adbotsuser.data.remote.HealthDrinksService
import javax.inject.Inject

class AuthRepository @Inject constructor(private val apiService: HealthDrinksService) {
    suspend fun login(req : LoginRequest) = apiService.login(req)
}