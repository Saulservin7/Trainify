package com.servin.trainify.exercises.domain.repository

import com.servin.trainify.exercises.domain.model.Result

interface ExerciseLikeRespository {

    suspend fun addExerciseLike(
        userId: String,
        exerciseId: String,
        like: Boolean
    ): Result<Unit>

    suspend fun getExerciseLikesByUserId(userId: String): Result<List<String>>

}