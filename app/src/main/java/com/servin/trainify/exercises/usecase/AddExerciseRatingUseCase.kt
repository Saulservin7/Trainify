package com.servin.trainify.exercises.usecase

import com.servin.trainify.exercises.data.model.ExerciseLikes
import javax.inject.Inject

class AddExerciseRatingUseCase @Inject constructor(
    private val exerciseLikeRepository: com.servin.trainify.exercises.domain.repository.ExerciseLikeRespository,
) {
    suspend operator fun invoke(
        exerciseLikes: ExerciseLikes
    ) = exerciseLikeRepository.addRating(exerciseLikes)
}