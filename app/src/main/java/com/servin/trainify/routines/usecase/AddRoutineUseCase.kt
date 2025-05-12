package com.servin.trainify.routines.usecase

import com.servin.trainify.routines.domain.repository.RoutinesRepository
import javax.inject.Inject

class AddRoutineUseCase @Inject constructor(
    private val repository: RoutinesRepository
) {

    suspend operator fun invoke(
        userUid: String,
        routine: com.servin.trainify.routines.domain.model.Routines
    ) = repository.addRoutine(userUid, routine)
}