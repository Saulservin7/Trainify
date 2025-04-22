package com.servin.trainify.exercises.usecase

import com.servin.trainify.exercises.data.model.Exercise
import com.servin.trainify.exercises.domain.model.Result
import com.servin.trainify.exercises.domain.repository.ExerciseRepository
import javax.inject.Inject


class AddExerciseUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    suspend operator fun invoke(exercise: Exercise): Result<Unit> {
        return repository.addExercise(exercise)
    }

}