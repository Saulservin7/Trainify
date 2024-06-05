package com.servin.trainify.domain.usecase

import com.servin.trainify.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val repository: AuthRepository) {
    operator fun invoke()=repository.logout()
}