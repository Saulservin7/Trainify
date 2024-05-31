package com.servin.trainify.domain.usecase

data class ExerciseUseCase(
    val insertExercise: InsertExercise,
    val getExercises: GetExercises,
    val getExercisebyId: GetExercisebyId,
    val updateExercise: UpdateExercise,
    val deleteExercise: DeleteExercise
)
