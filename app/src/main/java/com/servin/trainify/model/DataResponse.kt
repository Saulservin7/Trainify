package com.servin.trainify.model

sealed class DataResponse <out T> {
    data object Loading : DataResponse<Nothing>()

    data class Success<out T>(val data:T) : DataResponse<T>()

    data class Error <out T>(val e:Exception):DataResponse<T>()
}