package com.servin.trainify.domain.usecase

import com.servin.trainify.room.ExerciseRepository
import javax.inject.Inject

class GetExercisebyId @Inject constructor(private val reposity:ExerciseRepository)
{
    operator fun invoke(id:Int)=reposity.getExerciseById(id)
}




