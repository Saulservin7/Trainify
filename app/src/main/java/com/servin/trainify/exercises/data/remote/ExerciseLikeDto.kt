package com.servin.trainify.exercises.data.remote

import com.servin.trainify.exercises.data.model.ExerciseLikes

data class ExerciseLikeDto(
    val userId: String,
    val exerciseId: String,
    val like: Boolean
) {
    fun toDomain() = ExerciseLikes(
        userId = userId,
        exerciseId = exerciseId,
        like = like
    )

    companion object {
        fun fromDomain(exerciseLike: ExerciseLikes): ExerciseLikeDto = ExerciseLikeDto(
            userId = exerciseLike.userId,
            exerciseId = exerciseLike.exerciseId,
            like = exerciseLike.like
        )
    }
}

