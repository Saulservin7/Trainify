package com.servin.trainify.domain.usecase

import com.servin.trainify.data.model.Exercise
import com.servin.trainify.domain.model.Result
import com.servin.trainify.domain.repository.ExerciseRepository
import javax.inject.Inject

// domain/usecase/GetExercisesUseCase.kt
class GetExercisesUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    suspend operator fun invoke(): Result<List<Exercise>> {
        return repository.getExercises() // Retorna tu Result directamente
    }
}