package com.servin.trainify.domain.usecase

import com.servin.trainify.room.ExerciseRepository
import com.servin.trainify.ui.home.model.Exercise
import javax.inject.Inject

class DeleteExercise @Inject constructor (private val reposity:ExerciseRepository){

    suspend operator fun invoke(exercise: Exercise){
        reposity.deleteExercise(exercise)
    }
}