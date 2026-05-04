package com.iprism.adbotsuser.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iprism.adbotsuser.data.models.promotions.PromotionsItem
import com.iprism.adbotsuser.data.models.redeemrequest.RedeemRequestApiResponse
import com.iprism.adbotsuser.data.models.userdetails.UserDetailsApiResponse
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
class AnalyticsViewModel @Inject constructor(private val repository: PromotionsRepository) :
    ViewModel() {

    private val _promotions = MutableStateFlow<List<PromotionsItem>>(emptyList())
    val promotions = _promotions.asStateFlow()

    private val _userDetails = MutableStateFlow<UiState<UserDetailsApiResponse>>(UiState.Idle)
    val userDetails = _userDetails.asStateFlow()

    private val _uiState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val uiState: StateFlow<UiState<Unit>> = _uiState

    private val _redeemState = MutableStateFlow<UiState<RedeemRequestApiResponse>>(UiState.Idle)
    val redeemState: StateFlow<UiState<RedeemRequestApiResponse>> = _redeemState.asStateFlow()

    private val _redeemEvent = MutableSharedFlow<RedeemEvent>()
    val redeemEvent: SharedFlow<RedeemEvent> = _redeemEvent.asSharedFlow()

    sealed class RedeemEvent {
        data class Success(val message: String) : RedeemEvent()
        data class Error(val message: String) : RedeemEvent()
    }

    private val _isPaginationLoading = MutableStateFlow(false)
    val isPaginationLoading: StateFlow<Boolean> = _isPaginationLoading

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private var currentPage = 1
    private var isLastPage = false
    private var isFetching = false

    init {
        fetchUserDetails()
        fetchPromotions()
    }

    fun fetchPromotions() {
        if (isFetching || isLastPage) return
        viewModelScope.launch {
            performFetchPromotions()
        }
    }

    private suspend fun performFetchPromotions() {
        isFetching = true
        if (currentPage == 1) {
            _uiState.value = UiState.Loading
        } else {
            _isPaginationLoading.value = true
        }
        try {
            val response = repository.fetchPromotions(currentPage)
            if (response.status) {
                val newItems = response.response.promotions
                if (newItems.isNotEmpty()) {
                    if (currentPage == 1) {
                        _promotions.value = newItems
                    } else {
                        _promotions.value += newItems
                    }
                    val totalPages = response.response.pagination.totalPages.size
                    if (currentPage >= totalPages) {
                        isLastPage = true
                    } else {
                        currentPage++
                    }
                } else {
                    isLastPage = true
                }
                _uiState.value = UiState.Success(Unit)
            } else {
                if (currentPage == 1) {
                    _uiState.value = UiState.Error(response.message)
                }
            }
        } catch (e: Exception) {
            if (currentPage == 1) {
                _uiState.value = UiState.Error(e.localizedMessage ?: "Unknown error")
            }
        } finally {
            isFetching = false
            _isPaginationLoading.value = false
        }
    }

    fun fetchUserDetails() {
        viewModelScope.launch {
            performFetchUserDetails()
        }
    }

    private suspend fun performFetchUserDetails() {
        if (userDetails.value !is UiState.Success) {
            _userDetails.value = UiState.Loading
        }
        try {
            val response = repository.fetchUserDetails()
            if (response.status) {
                _userDetails.value = UiState.Success(response)
            } else {
                _userDetails.value = UiState.Error(response.message)
            }
        } catch (e: Exception) {
            _userDetails.value = UiState.Error(e.localizedMessage ?: "Unknown error")
        }
    }

    fun logout(onComplete: () -> Unit) {
        viewModelScope.launch {
            repository.logout()
            onComplete()
        }
    }

    fun redeemRequest(amount: String) {
        viewModelScope.launch {
            _redeemState.value = UiState.Loading
            try {
                val response = repository.redeemRequest(amount)
                if (response.status) {
                    _redeemState.value = UiState.Success(response)
                    _redeemEvent.emit(RedeemEvent.Success(response.message))
                } else {
                    _redeemState.value = UiState.Error(response.message)
                    _redeemEvent.emit(RedeemEvent.Error(response.message))
                }
            } catch (e: Exception) {
                val errorMsg = e.localizedMessage ?: "Unknown error"
                _redeemState.value = UiState.Error(errorMsg)
                _redeemEvent.emit(RedeemEvent.Error(errorMsg))
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            currentPage = 1
            isLastPage = false

            // Launch both concurrently and wait for them
            val job1 = launch { performFetchUserDetails() }
            val job2 = launch { performFetchPromotions() }

            job1.join()
            job2.join()
            _isRefreshing.value = false
        }
    }
}
