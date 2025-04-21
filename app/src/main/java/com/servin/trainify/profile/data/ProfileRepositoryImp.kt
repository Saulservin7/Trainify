package com.servin.trainify.profile.data

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.servin.trainify.auth.domain.model.User
import com.servin.trainify.profile.data.remote.UserDto
import com.servin.trainify.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.tasks.await

class ProfileRepositoryImp
    (
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
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

    // En tu clase o inicialización (por ejemplo, en un módulo de Koin o en el Application):
    private val firebaseStorage = Firebase.storage("gs://trainify-f04c5.firebasestorage.app")

    // En tu repositorio o DataSource:
    override suspend fun updateUserPhoto(uri: Uri): String? {
        val uid = auth.currentUser?.uid ?: throw Exception("No auth")
        val filename = "${uid}_${System.currentTimeMillis()}.jpg"

        // Usa la instancia de FirebaseStorage con el bucket correcto
        val storageRef = firebaseStorage.reference.child("profile_pics/$filename") // <-- ¡Clave aquí!

        storageRef.putFile(uri).await()
        return storageRef.downloadUrl.await().toString()
    }

}