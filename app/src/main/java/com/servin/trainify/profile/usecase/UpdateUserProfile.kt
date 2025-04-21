package com.servin.trainify.profile.usecase

import com.servin.trainify.auth.domain.model.User
import com.servin.trainify.profile.domain.repository.ProfileRepository

class UpdateUserProfile(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(userProfile: User) {
        repository.updateUserProfile(userProfile)
    }
}