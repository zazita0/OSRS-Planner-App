package com.osrsplanner.app.data.repository

import com.osrsplanner.app.data.database.UserProfileDao
import com.osrsplanner.app.data.models.UserProfile
import com.osrsplanner.app.data.network.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userProfileDao: UserProfileDao,
    private val apiService: ApiService
) {
    
    fun getCurrentUserProfile(): Flow<UserProfile?> {
        return userProfileDao.getUserProfile("current_user")
    }
    
    suspend fun updateUserProfile(profile: UserProfile) {
        userProfileDao.updateUserProfile(profile)
        // Sync with server
        try {
            apiService.syncUserData(profile)
        } catch (e: Exception) {
            // Handle sync error
        }
    }
    
    suspend fun createUserProfile(profile: UserProfile) {
        userProfileDao.insertUserProfile(profile)
    }
    
    suspend fun deleteUserProfile(profile: UserProfile) {
        userProfileDao.deleteUserProfile(profile)
    }
    
    suspend fun fetchUserProfileFromServer(userId: String): UserProfile? {
        return try {
            val response = apiService.getUserStats(userId)
            response.data
        } catch (e: Exception) {
            null
        }
    }
    
    suspend fun getRecentActivity(): List<String> {
        // Mock recent activity for now
        return listOf(
            "Completed Dragon Slayer quest",
            "Reached level 80 in Attack",
            "Defeated Vorkath 10 times",
            "Saved new gear setup"
        )
    }
} 