package com.iprism.adbotsuser.data.repositories

import com.iprism.adbotsuser.data.models.login.LoginRequest
import com.iprism.adbotsuser.data.remote.HealthDrinksService
import com.iprism.adbotsuser.utils.DataStoreManager
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: HealthDrinksService, private val dataStoreManager: DataStoreManager
) {
    suspend fun login(request: LoginRequest) =
        apiService.login(request).also { response ->
            if (response.status) {
                val user = response.response.userDetails
                dataStoreManager.saveUser(
                    userId = user.id,
                    userName = user.name,
                    token = user.token
                )
                dataStoreManager.loginUser()
            }
        }
}