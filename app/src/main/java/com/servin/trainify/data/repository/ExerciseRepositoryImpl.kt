package com.servin.trainify.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.servin.trainify.data.model.Exercise
import com.servin.trainify.domain.repository.ExerciseRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import com.servin.trainify.domain.model.Result // ¡Importa tu clase!


class ExerciseRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : ExerciseRepository {
    override suspend fun addExercise(exercise: Exercise): Result<Unit> {
        return try {
            firestore.collection("exercises")
                .add(exercise)
                .await()
           Result.success(Unit)
        } catch (e: Exception) {
            Result.Error("Error adding Exercise:${e.message}")
        }
    }

    override suspend fun getExercises(): Result<List<Exercise>> {
        return try {
            val snapshot = firestore.collection("exercises").get().await()
            val exercises = snapshot.documents.map { doc ->
                doc.toObject(Exercise::class.java)!!
            }
            Result.success(exercises)
        } catch (e: Exception) {
            Result.error("Error Obteniendo Ejercicios:${e.message}")
        }
    }

    override suspend fun getExerciseByObjective(objective: String): Result<List<Exercise>> {
        return try {


            val snapshot = firestore.collection("exercises")
                .whereEqualTo("objective", objective)
                .get()
                .await()
            val exercises = snapshot.documents.map { doc ->
                doc.toObject(Exercise::class.java)!!
            }
            Result.success(exercises)
        } catch (e: Exception) {
            Result.error("Error Obteniendo Ejercicios:${e.message}")
        }
    }

    override suspend fun getExerciseBySportContext(sportContext: String): Result<List<Exercise>> {
        return try {
            val snapshot = firestore.collection("exercises")
                .whereEqualTo("sportContext", sportContext)
                .get()
                .await()
            val exercises = snapshot.documents.map { doc ->
                doc.toObject(Exercise::class.java)!!
            }

            Result.success(exercises)
        } catch (e: Exception) {
            Result.error("Error Obteniendo Ejercicios:${e.message}")
        }
    }

}