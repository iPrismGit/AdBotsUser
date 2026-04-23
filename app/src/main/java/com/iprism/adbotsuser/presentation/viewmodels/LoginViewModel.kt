package com.iprism.adbotsuser.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iprism.adbotsuser.data.models.login.LoginApiResponse
import com.iprism.adbotsuser.data.models.login.LoginRequest
import com.iprism.adbotsuser.data.repositories.AuthRepository
import com.iprism.adbotsuser.utils.DataStoreManager
import com.iprism.adbotsuser.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.toString

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {

    private val _loginResponse = MutableStateFlow<UiState<LoginApiResponse>>(UiState.Idle)
    val loginResponse: StateFlow<UiState<LoginApiResponse>> = _loginResponse

    private val _event = MutableSharedFlow<LoginEvent>()
    val event = _event.asSharedFlow()

    sealed class LoginEvent {
        object NavigateToHome : LoginEvent()
        data class Error(val message: String) : LoginEvent()
    }

    fun login(request: LoginRequest) {
        val validationError = validateLogin(request.userName, request.password)
        if (validationError != null) {
            viewModelScope.launch {
                _event.emit(LoginEvent.Error(validationError))
            }
            return
        }
        viewModelScope.launch {
            _loginResponse.value = UiState.Loading
            try {
                Log.d("requestLoading", request.toString())
                val response = repository.login(request)
                if (response.status) {
                    Log.d("requestLoading", response.response.userDetails.toString())
                    _loginResponse.value = UiState.Success(response)
                    _event.emit(LoginEvent.NavigateToHome)
                } else {
                    _event.emit(LoginEvent.Error(response.message))
                    _loginResponse.value = UiState.Error(response.message)
                }
            } catch (e: Exception) {
                _loginResponse.value = UiState.Error(e.localizedMessage ?: "Unknown error")
                _event.emit(LoginEvent.Error(e.localizedMessage ?: "Unknown error"))
            }
        }
    }

    fun validateLogin(userId: String, password: String): String? {
        return when {
            userId.isBlank() -> "User Id is required"
            password.isBlank() -> "Password is required"
            !password.equals("604020", true) -> "Invalid password"
            else -> null
        }
    }
}