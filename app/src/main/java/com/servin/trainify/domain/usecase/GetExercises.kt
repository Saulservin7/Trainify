package com.servin.trainify.domain.usecase

import com.servin.trainify.room.ExerciseRepository
import javax.inject.Inject

class GetExercises @Inject constructor(private val reposity: ExerciseRepository){

    operator fun invoke() = reposity.getAllExercises()


}