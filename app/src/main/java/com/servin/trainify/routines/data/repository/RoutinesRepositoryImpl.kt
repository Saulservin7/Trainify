package com.servin.trainify.routines.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.servin.trainify.exercises.domain.model.Result
import com.servin.trainify.routines.data.model.RoutinesDto
import com.servin.trainify.routines.domain.model.Routines
import com.servin.trainify.routines.domain.repository.RoutinesRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RoutinesRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
) : RoutinesRepository {
    override suspend fun addRoutine(userUid: String, routine: Routines): Result<Unit> {
        return try {
            val routineId = "${userUid}_${System.currentTimeMillis()}"
            firestore.collection("routines")
                .document(routineId)
                .set(routine)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.Error("Error adding Exercise: ${e.message}")
        }
    }

    override suspend fun getRoutines(): Result<List<Routines>> {
       return try {
            val snapshot = firestore.collection("routines").whereEqualTo("isPublic",true).get().await()

           val routines = snapshot.documents.mapNotNull {
               doc -> doc.toObject(RoutinesDto::class.java)?.toDomain()
           }
              Result.success(routines)
       }catch (e:Exception){
              Result.Error("Error getting Routines: ${e.message}")
       }
    }
}