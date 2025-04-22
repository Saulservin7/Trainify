package com.servin.trainify.exercises.domain.model

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()  // Simplificado
    object Loading : Result<Nothing>()
    object Idle : Result<Nothing>()  // Nuevo estado inicial

    companion object {
        fun <T> success(data: T): Result<T> = Success(data)
        fun error(message: String): Result<Nothing> = Error(message)
        fun loading(): Result<Nothing> = Loading
        fun idle(): Result<Nothing> = Idle
    }
}