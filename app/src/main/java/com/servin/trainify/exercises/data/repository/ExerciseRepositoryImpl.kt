package com.servin.trainify.exercises.data.repository

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.servin.trainify.exercises.data.model.Exercise
import com.servin.trainify.exercises.domain.repository.ExerciseRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import com.servin.trainify.exercises.domain.model.Result // ¡Importa tu clase!
import androidx.core.net.toUri
import com.servin.trainify.exercises.data.remote.ExerciseDto


class ExerciseRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) : ExerciseRepository {


    override suspend fun addExercise(
        exercise: Exercise,
        mediaUris: List<String>,
        userUid: String
    ): Result<Unit> {

        return try {
            // Generar un ID único para el ejercicio
            val exerciseId = "${userUid}_${System.currentTimeMillis()}"
            val storageRef = storage.reference.child("exercises/$exerciseId")

            // Subir cada archivo
            val mediaUrls = mediaUris.mapIndexed { index, uri ->
                val fileRef = storageRef.child("$index")
                try {
                    val uriToUpload = uri.toUri()  // Convierte el String a Uri
                    fileRef.putFile(uriToUpload).await()
                } catch (e: Exception) {
                    return Result.Error("Bistec: ${e.message}")
                } // Esperamos que la carga termine
                fileRef.downloadUrl.await().toString() // Obtenemos la URL del archivo subido
            }

            // C
            // rear un nuevo objeto Exercise con las URLs de los archivos
            val exerciseWithUrls = exercise.copy(
                id = exerciseId,          // Usamos el ID generado
                mediaUrls = mediaUrls     // Guardamos las URLs de los archivos
            )

            // Guardar el ejercicio en Firestore
            firestore.collection("exercises")
                .add(exerciseWithUrls)  // Guardamos el ejercicio en la colección
                .await()  // Esperamos que la operación termine

            Result.success(Unit)  // Retornamos éxito
        } catch (e: Exception) {
            Result.Error("Error adding Exercise: ${e.message}")  // En caso de error
        }
    }

    override suspend fun getExercises(): Result<List<Exercise>> {
        return try {
            val snapshot = firestore.collection("exercises").get().await()

            val exercises = snapshot.documents.mapNotNull { doc ->
                doc.toObject(ExerciseDto::class.java)?.toDomain()
            }

            Result.success(exercises)
        } catch (e: Exception) {
            Result.error("Error Obteniendo Ejercicios: ${e.message}")
        }
    }

    override suspend fun getExerciseByObjective(objective: String): Result<List<Exercise>> {
        return try {
            val snapshot = firestore.collection("exercises")
                .whereEqualTo("objective", objective)
                .get()
                .await()
            val exercises = snapshot.documents.mapNotNull { doc ->
                doc.toObject(ExerciseDto::class.java)?.toDomain()
            }
            Result.success(exercises)
        } catch (e: Exception) {
            Result.error("Error Obteniendo Ejercicios:${e.message}")
        }
    }

    override suspend fun getExerciseById(id:String):Result<Exercise>{
        return try{
            val snapshot = firestore.collection("exercises")
                .whereEqualTo("id", id)
                .get()
                .await()
            val exercise = snapshot.documents.firstNotNullOfOrNull { doc ->
                doc.toObject(ExerciseDto::class.java)?.toDomain()
            }

            if (exercise != null) {
                Result.success(exercise)
            } else {
                Result.error("Ejercicio no encontrado")
            }
        } catch (e:Exception){
            Result.error("Error Obteniendo Ejercicio:${e.message}")
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