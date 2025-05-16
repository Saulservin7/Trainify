package com.servin.trainify.routines.usecase

import com.servin.trainify.routines.domain.repository.RoutinesRepository
import javax.inject.Inject

class LoadExercisesUseCase @Inject constructor(
    private val repository: RoutinesRepository
) {

    suspend operator fun invoke(exercisesList: List<String>) =
        repository.loadExercises(exercisesList)
}