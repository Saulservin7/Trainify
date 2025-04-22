package com.servin.trainify.auth

import com.google.firebase.auth.FirebaseAuth
import com.servin.trainify.auth.domain.repository.AuthRepository
import javax.inject.Inject

class UserSessionManager @Inject constructor(
    private val auth: FirebaseAuth
) {
    fun getCurrentUserUid(): String {
        return auth.currentUser?.uid ?: ""
    }
}