package com.servin.trainify.auth.domain.model

// Crea este archivo en: domain/model/Result.kt
sealed class Result<T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error<T>(val message: String) : Result<T>()
}

