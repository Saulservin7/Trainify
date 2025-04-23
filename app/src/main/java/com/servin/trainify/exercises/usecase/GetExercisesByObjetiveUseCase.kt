package com.servin.trainify.exercises.usecase

import com.servin.trainify.exercises.domain.repository.ExerciseRepository
import javax.inject.Inject

class GetExercisesByObjetiveUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    suspend operator fun invoke(objective: String) = repository.getExerciseByObjective(objective)
}