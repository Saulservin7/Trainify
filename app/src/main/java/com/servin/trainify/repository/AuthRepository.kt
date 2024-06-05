package com.servin.trainify.repository

import com.google.firebase.auth.FirebaseUser
import com.servin.trainify.model.DataResponse

interface AuthRepository {
    val currentUser:FirebaseUser?

    suspend fun Login(email:String, password:String):DataResponse<FirebaseUser>
    suspend fun Register(email:String, password:String):DataResponse<FirebaseUser>

    fun logout()
}