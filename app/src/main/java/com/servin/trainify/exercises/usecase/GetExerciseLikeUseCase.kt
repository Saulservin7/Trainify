package com.servin.trainify.exercises.usecase

import com.servin.trainify.exercises.domain.repository.ExerciseLikeRespository
import javax.inject.Inject

class GetExerciseLikeUseCase @Inject constructor(
    private val repository : ExerciseLikeRespository
) {

    suspend operator fun invoke(
        userId: String,
        exerciseId: String
    ) = repository.getExerciseLike(userId, exerciseId)
}