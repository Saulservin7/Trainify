package com.servin.trainify.exercises.data.remote

import com.servin.trainify.exercises.data.model.ExerciseLikes

data class ExerciseLikeDto(
    val userId: String = "",
    val exerciseId: String = "",
    val like: Boolean = false,
    val rating: Int = -1,
) {
    fun toDomain() = ExerciseLikes(
        userId = userId,
        exerciseId = exerciseId,
        like = like,
        rating = rating
    )

    companion object {
        fun fromDomain(exerciseLike: ExerciseLikes): ExerciseLikeDto = ExerciseLikeDto(
            userId = exerciseLike.userId,
            exerciseId = exerciseLike.exerciseId,
            like = exerciseLike.like,
            rating = exerciseLike.rating
        )
    }
}

