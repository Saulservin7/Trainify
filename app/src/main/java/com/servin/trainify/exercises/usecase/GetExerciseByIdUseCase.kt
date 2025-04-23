package com.servin.trainify.exercises.usecase

import com.servin.trainify.exercises.domain.repository.ExerciseRepository
import javax.inject.Inject

class GetExerciseByIdUseCase @Inject constructor(private val repository: ExerciseRepository) {
    suspend operator fun invoke(id: String) = repository.getExerciseById(id)
}