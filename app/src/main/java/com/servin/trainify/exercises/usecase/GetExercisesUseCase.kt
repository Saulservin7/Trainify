package com.servin.trainify.exercises.usecase

import com.servin.trainify.exercises.data.model.Exercise
import com.servin.trainify.exercises.domain.model.Result
import com.servin.trainify.exercises.domain.repository.ExerciseRepository
import javax.inject.Inject

// domain/usecase/GetExercisesUseCase.kt
class GetExercisesUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    suspend operator fun invoke(): Result<List<Exercise>> {
        return repository.getExercises() // Retorna tu Result directamente
    }
}