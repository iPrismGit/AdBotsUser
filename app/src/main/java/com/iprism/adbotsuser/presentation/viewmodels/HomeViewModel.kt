package com.iprism.adbotsuser.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iprism.adbotsuser.data.models.login.LoginRequest
import com.iprism.adbotsuser.data.models.promotions.PromotionsApiResponse
import com.iprism.adbotsuser.data.models.promotions.PromotionsRequest
import com.iprism.adbotsuser.data.repositories.PromotionsRepository
import com.iprism.adbotsuser.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.toString

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: PromotionsRepository) : ViewModel() {

    private val _promotionsResponse = MutableStateFlow<UiState<PromotionsApiResponse>>(UiState.Idle)
    val promotionsResponse: StateFlow<UiState<PromotionsApiResponse>> = _promotionsResponse

    fun login() {
        viewModelScope.launch {
            _promotionsResponse.value = UiState.Loading
            try {
                val response = repository.fetchPromotions(1)
                if (response.status) {
                    _promotionsResponse.value = UiState.Success(response)
                } else {
                    _promotionsResponse.value = UiState.Error(response.message)
                }
            } catch (e: Exception) {
                _promotionsResponse.value = UiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}