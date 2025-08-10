package com.osrsplanner.app.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osrsplanner.app.data.models.UserProfile
import com.osrsplanner.app.data.repository.UserRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    
    private val _userProfile = MutableLiveData<UserProfile>()
    val userProfile: LiveData<UserProfile> = _userProfile
    
    private val _recentActivity = MutableLiveData<List<String>>()
    val recentActivity: LiveData<List<String>> = _recentActivity
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error
    
    init {
        loadUserProfile()
        loadRecentActivity()
    }
    
    private fun loadUserProfile() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val profile = userRepository.getCurrentUserProfile()
                _userProfile.value = profile
            } catch (e: Exception) {
                _error.value = "Failed to load user profile: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    private fun loadRecentActivity() {
        viewModelScope.launch {
            try {
                val activities = userRepository.getRecentActivity()
                _recentActivity.value = activities
            } catch (e: Exception) {
                _error.value = "Failed to load recent activity: ${e.message}"
            }
        }
    }
    
    fun refreshData() {
        loadUserProfile()
        loadRecentActivity()
    }
    
    fun clearError() {
        _error.value = null
    }
} 