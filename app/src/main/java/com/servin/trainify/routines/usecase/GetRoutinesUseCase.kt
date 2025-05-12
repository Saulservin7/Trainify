package com.servin.trainify.routines.usecase

import com.servin.trainify.exercises.domain.model.Result
import com.servin.trainify.routines.domain.model.Routines
import com.servin.trainify.routines.domain.repository.RoutinesRepository
import javax.inject.Inject

class GetRoutinesUseCase @Inject constructor(
    private val repository : RoutinesRepository
) {
    suspend operator fun invoke(): Result<List<Routines>> {

    return repository.getRoutines()
    }
}