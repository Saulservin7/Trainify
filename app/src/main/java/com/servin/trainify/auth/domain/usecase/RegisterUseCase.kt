package com.servin.trainify.auth.domain.usecase

import com.servin.trainify.auth.domain.model.Result
import com.servin.trainify.auth.domain.model.User
import com.servin.trainify.auth.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(
        email: String,
        password: String,
        name: String
    ): Result<User> {
        return authRepository.register(email,password,name)

    }
}