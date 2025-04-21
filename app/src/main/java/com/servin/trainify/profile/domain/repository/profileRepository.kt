package com.servin.trainify.profile.domain.repository

import android.net.Uri
import com.servin.trainify.auth.domain.model.User

interface ProfileRepository {
    suspend fun getUserProfile(): User?
    suspend fun updateUserProfile(userProfile: User)
    suspend fun updateUserPhoto(uri: Uri): String?

}