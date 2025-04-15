package com.servin.trainify.domain.usecase

import com.servin.trainify.auth.domain.model.User
import com.servin.trainify.auth.domain.repository.AuthRepository
import javax.inject.Inject

class GetUserData @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke():User? {
        return repository.getCurrentUser()
    }


}