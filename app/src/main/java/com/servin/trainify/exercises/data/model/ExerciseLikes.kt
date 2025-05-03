package com.servin.trainify.exercises.data.model

data class ExerciseLikes(
    val userId: String,
    val exerciseId: String,
    val like: Boolean,
    val rating: Int=-1,
)
