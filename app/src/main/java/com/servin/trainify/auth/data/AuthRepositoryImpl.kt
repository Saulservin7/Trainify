package com.servin.trainify.auth.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.servin.trainify.auth.domain.model.User
import com.servin.trainify.auth.domain.repository.AuthRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import com.servin.trainify.auth.domain.model.Result

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {
    override suspend fun login(email: String, password: String): Result<User> {
        return try {
            // 1. Autenticar usuario
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user ?: throw Exception("User Not Found")

            // 2. Obtener datos adicionales de Firestore
            val document = firestore.collection("users")
                .document(firebaseUser.uid)
                .get()
                .await()

            // 3. Construir objeto User con todos los datos
            val name = document.getString("name") ?: ""
            val user = User(
                id = firebaseUser.uid,
                email = firebaseUser.email ?: "",
                name = name
            )

            Result.Success(user)

        } catch (e: Exception) {
            Result.Error(e.message ?: "Error en el login")
        }
    }

    override suspend fun register(email: String, password: String,name:String): Result<User> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email,password).await()
            val firebaseUser= authResult.user ?: throw Exception("User Not Found")

            val userData = hashMapOf(
                "name" to name,
                "email" to email
            )
            firestore.collection("users")
                .document(firebaseUser.uid)
                .set(userData)
                .await()
            val user = firebaseUser.toUser()
            Result.Success(user)

        }
        catch (e:Exception){
            Result.Error(e.message ?: "Ocurrio un error")
        }
    }



    override fun logout() {
        return auth.signOut()
    }

    override suspend fun getCurrentUser(): User? {
        val firebaseUser = auth.currentUser ?: return null

        return try {
            val document = firestore.collection("users")
                .document(firebaseUser.uid)
                .get()
                .await()

            User(
                id = firebaseUser.uid,
                email = firebaseUser.email ?: "",
                name = document.getString("name") ?: ""
            )
        } catch (e: Exception) {
            null
        }
    }


    private fun FirebaseUser.toUser(): User {
        return User(
            id = this.uid,
            email = this.email ?: "",
            name = "" // Valor temporal, será sobreescrito desde Firestore
        )
    }

}