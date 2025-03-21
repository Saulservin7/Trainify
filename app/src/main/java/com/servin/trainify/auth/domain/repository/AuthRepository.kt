package com.servin.trainify.auth.domain.repository

import com.servin.trainify.auth.domain.model.User
import com.servin.trainify.auth.domain.model.Result

interface AuthRepository {

    suspend fun login(email: String, password: String): Result<User>
    suspend fun register(email: String, password: String): Result<User>
    fun logout()
    fun getCurrentUser(): User?
}