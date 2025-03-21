package com.servin.trainify.domain.usecase

import com.servin.trainify.data.model.Exercise
import com.servin.trainify.domain.model.Result
import com.servin.trainify.domain.repository.ExerciseRepository
import com.servin.trainify.navigation.Routes
import javax.inject.Inject

class AddExerciseUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    suspend operator fun invoke(exercise: Exercise): Result<Unit> {
        return repository.addExercise(exercise)
    }

}