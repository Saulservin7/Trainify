package com.servin.trainify.exercises.usecase

import android.net.Uri
import com.servin.trainify.exercises.data.model.Exercise
import com.servin.trainify.exercises.domain.model.Result
import com.servin.trainify.exercises.domain.repository.ExerciseRepository
import javax.inject.Inject


class AddExerciseUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    suspend operator fun invoke(
        exercise: Exercise,
        mediaUris: List<String>,  // Parámetro adicional para las URIs de los archivos
        userUid: String        // Parámetro adicional para el UID del usuario
    ): Result<Unit> {
        return repository.addExercise(exercise, mediaUris, userUid)  // Llamada al repositorio con los nuevos parámetros
    }
}