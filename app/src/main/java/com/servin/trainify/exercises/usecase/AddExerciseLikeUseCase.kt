package com.servin.trainify.exercises.usecase

import javax.inject.Inject

class AddExerciseLikeUseCase @Inject constructor(
    private val exerciseLikeRepository: com.servin.trainify.exercises.domain.repository.ExerciseLikeRespository,
) {
    suspend operator fun invoke(
        userId: String,
        exerciseId: String,
        like: Boolean
    ) = exerciseLikeRepository.addExerciseLike(userId, exerciseId, like)
}