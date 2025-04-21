package com.servin.trainify.profile.usecase

import com.servin.trainify.profile.domain.repository.ProfileRepository

class GetUserProfile(
    private val repository: ProfileRepository
){
    suspend operator fun invoke() = repository.getUserProfile()

}