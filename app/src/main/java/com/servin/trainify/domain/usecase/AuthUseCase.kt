package com.servin.trainify.domain.usecase

import com.servin.trainify.repository.AuthRepository
import javax.inject.Inject

data class AuthUseCase (
    val getCurrentUser: GetCurrentUser,
    val login: LoginUseCase,
    val logout: LogoutUseCase,
    val register: RegisterUseCase
)



