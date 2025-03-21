package com.servin.trainify.auth.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.servin.trainify.auth.domain.model.User
import com.servin.trainify.auth.domain.repository.AuthRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import com.servin.trainify.auth.domain.model.Result

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {
    override suspend fun login(email: String, password: String): Result<User> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
           Result.Success(result.user?.toUser()?: throw Exception("User Not Found"))

        } catch (
            e: Exception
        ) {
            Result.Error(e.message ?: "Ocurrio un error")
        }

    }

    override suspend fun register(email: String, password: String): Result<User> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email,password).await()
            val firebaseUser= authResult.user ?: throw Exception("User Not Found")
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

    override fun getCurrentUser(): User? {
        return auth.currentUser?.toUser()
    }


    private fun FirebaseUser?.toUser(): User {
        return User(
            id = this?.uid ?: "",
            email = this?.email ?: ""
        )
    }

}