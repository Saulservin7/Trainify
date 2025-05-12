package com.servin.trainify.routines.domain.repository

import com.servin.trainify.exercises.domain.model.Result
import com.servin.trainify.routines.domain.model.Routines

interface RoutinesRepository {

    suspend fun addRoutine(userUid: String,routine:Routines): Result<Unit>

    suspend fun getRoutines(): Result<List<Routines>>

  //  suspend fun getRoutineById(id: String): Result<Routines>


}