package com.servin.trainify.auth.domain.usecase

import com.servin.trainify.auth.domain.model.User
import com.servin.trainify.auth.domain.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() {

        return authRepository.logout()
    }
}
