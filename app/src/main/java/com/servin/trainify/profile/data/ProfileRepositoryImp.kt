package com.servin.trainify.profile.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.servin.trainify.auth.domain.model.User
import com.servin.trainify.profile.data.remote.UserDto
import com.servin.trainify.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.tasks.await

class ProfileRepositoryImp
    (
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : ProfileRepository {

    override suspend fun getUserProfile(): User? {
        val uuid = auth.currentUser?.uid ?: return null
        val userDocument = firestore.collection("users").document(uuid).get().await()
        return if (userDocument.exists()) {
            userDocument.toObject(UserDto::class.java)?.toDomain()
        } else {
            null
        }

    }

    override suspend fun updateUserProfile(userProfile: User) {
        val uuid = auth.currentUser?.uid ?: return
        val userDto = UserDto.fromDomain(userProfile)
        firestore.collection("users").document(uuid).set(userDto).await()
    }

}