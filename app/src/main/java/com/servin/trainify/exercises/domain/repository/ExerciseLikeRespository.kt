package com.servin.trainify.exercises.domain.repository

import com.servin.trainify.exercises.data.model.ExerciseLikes
import com.servin.trainify.exercises.data.remote.ExerciseLikeDto
import com.servin.trainify.exercises.domain.model.Result

interface ExerciseLikeRespository {

    suspend fun addExerciseLike(
        exerciseLike: ExerciseLikes
    ): Result<Unit>


    suspend fun addRating(exerciseLike: ExerciseLikes):Result<Unit>




    suspend fun getExerciseLikesByUserId(userId: String): Result<List<String>>


    suspend fun getExerciseLike(userId: String, exerciseId: String): Result<ExerciseLikes>
}