package com.servin.trainify.domain.usecase

import com.servin.trainify.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor (private val repository: AuthRepository) {
    suspend operator fun invoke(email:String, password:String)= repository.Register(email, password)
}