package com.iprism.adbotsuser.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iprism.adbotsuser.data.models.promotions.PromotionsItem
import com.iprism.adbotsuser.data.models.userdetails.UserDetailsApiResponse
import com.iprism.adbotsuser.data.repositories.PromotionsRepository
import com.iprism.adbotsuser.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnalyticsViewModel @Inject constructor(private val repository: PromotionsRepository) :
    ViewModel() {

    private val _promotions = MutableStateFlow<List<PromotionsItem>>(emptyList())
    val promotions: StateFlow<List<PromotionsItem>> = _promotions

    private val _userDetails = MutableStateFlow<UiState<UserDetailsApiResponse>>(UiState.Idle)
    val userDetails: StateFlow<UiState<UserDetailsApiResponse>> = _userDetails

    private val _uiState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val uiState: StateFlow<UiState<Unit>> = _uiState

    private val _isPaginationLoading = MutableStateFlow(false)
    val isPaginationLoading: StateFlow<Boolean> = _isPaginationLoading

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
                        _promotions.value += newItems
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
    }

    fun fetchUserDetails() {
        viewModelScope.launch {
            _userDetails.value = UiState.Loading
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
    }

    fun logout(onComplete: () -> Unit) {
        viewModelScope.launch {
            repository.logout()
            onComplete()
        }
    }
}