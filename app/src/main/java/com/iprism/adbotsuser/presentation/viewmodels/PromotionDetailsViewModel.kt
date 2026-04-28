package com.iprism.adbotsuser.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iprism.adbotsuser.data.models.promotiondetails.PromotionDetailsApiResponse
import com.iprism.adbotsuser.data.models.promotiondetails.PromotionDetailsRequest
import com.iprism.adbotsuser.data.models.report.ReportApiResponse
import com.iprism.adbotsuser.data.repositories.PromotionsRepository
import com.iprism.adbotsuser.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PromotionDetailsViewModel @Inject constructor(
    private val repository: PromotionsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _response = MutableStateFlow<UiState<PromotionDetailsApiResponse>>(UiState.Idle)
    val response: StateFlow<UiState<PromotionDetailsApiResponse>> = _response.asStateFlow()

    private val _reportResponse = MutableStateFlow<UiState<ReportApiResponse>>(UiState.Idle)
    val reportResponse: StateFlow<UiState<ReportApiResponse>> = _reportResponse.asStateFlow()

    private val _reportEvent = MutableSharedFlow<ReportEvent>()
    val reportEvent: SharedFlow<ReportEvent> = _reportEvent.asSharedFlow()

    sealed class ReportEvent {
        data class Success(val message: String) : ReportEvent()
        data class Error(val message: String) : ReportEvent()
    }

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

    fun userReport(promotionId: String) {
        viewModelScope.launch {
            _reportResponse.value = UiState.Loading
            try {
                val response = repository.userReport(promotionId)
                if (response.status) {
                    _reportResponse.value = UiState.Success(response)
                    _reportEvent.emit(ReportEvent.Success(response.message))
                } else {
                    _reportResponse.value = UiState.Error(response.message)
                    _reportEvent.emit(ReportEvent.Error(response.message))
                }
            } catch (e: Exception) {
                Log.e("PromotionDetailsVM", "ReportError", e)
                val errorMsg = e.localizedMessage ?: "Unknown error"
                _reportResponse.value = UiState.Error(errorMsg)
                _reportEvent.emit(ReportEvent.Error(errorMsg))
            } finally {
                _reportResponse.value = UiState.Idle
            }
        }
    }
}
