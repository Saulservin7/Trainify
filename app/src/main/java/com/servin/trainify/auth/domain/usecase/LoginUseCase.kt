package com.servin.trainify.auth.domain.usecase

import com.servin.trainify.auth.domain.model.Result
import com.servin.trainify.auth.domain.model.User
import com.servin.trainify.auth.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(email: String, password: String): Result<User> {
        return authRepository.login(email, password)
    }
}