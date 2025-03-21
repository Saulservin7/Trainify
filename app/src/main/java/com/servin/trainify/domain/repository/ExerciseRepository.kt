package com.servin.trainify.domain.repository

import com.servin.trainify.data.model.Exercise
import com.servin.trainify.domain.model.Result // ¡Importa tu clase!


interface ExerciseRepository{
    suspend fun addExercise(exercise: Exercise):Result<Unit>
    suspend fun getExercises(): Result<List<Exercise>>
    suspend fun getExerciseByObjective(objective:String):Result<List<Exercise>>
    suspend fun getExerciseBySportContext(sportContext:String):Result<List<Exercise>>
}