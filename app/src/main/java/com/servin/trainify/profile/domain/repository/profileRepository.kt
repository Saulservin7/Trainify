package com.servin.trainify.profile.domain.repository

import com.servin.trainify.auth.domain.model.User

interface ProfileRepository {
    suspend fun getUserProfile(): User?
    suspend fun updateUserProfile(userProfile: User)

}