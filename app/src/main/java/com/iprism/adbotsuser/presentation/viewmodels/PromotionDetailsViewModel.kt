package com.iprism.adbotsuser.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iprism.adbotsuser.data.models.promotiondetails.PromotionDetailsApiResponse
import com.iprism.adbotsuser.data.models.promotiondetails.PromotionDetailsRequest
import com.iprism.adbotsuser.data.repositories.PromotionsRepository
import com.iprism.adbotsuser.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PromotionDetailsViewModel @Inject constructor(
    private val repository: PromotionsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _response = MutableStateFlow<UiState<PromotionDetailsApiResponse>>(UiState.Idle)
    val response: StateFlow<UiState<PromotionDetailsApiResponse>> = _response

    init {
        savedStateHandle.get<String>("id")?.let { id ->
            fetchPromotionDetails(id)
        }
    }

    fun fetchPromotionDetails(promotionId: String) {
        viewModelScope.launch {
            _response.value = UiState.Loading
            try {
                val user = repository.getUser()
                val request = PromotionDetailsRequest(
                    userId = user.userId?.toIntOrNull() ?: 0,
                    promotionId = promotionId,
                    authToken = user.token ?: ""
                )
                Log.d("PromotionDetailsReq", request.toString())
                val response = repository.fetchPromotionDetails(promotionId)
                if (response.status) {
                    _response.value = UiState.Success(response)
                } else {
                    _response.value = UiState.Error(response.message)
                }
            } catch (e: Exception) {
                Log.e("PromotionDetailsVM", "Error", e)
                _response.value = UiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}