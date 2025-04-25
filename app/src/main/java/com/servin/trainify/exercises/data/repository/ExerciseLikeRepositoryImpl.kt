package com.servin.trainify.exercises.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.servin.trainify.exercises.data.remote.ExerciseDto
import com.servin.trainify.exercises.domain.model.Result
import com.servin.trainify.exercises.domain.repository.ExerciseLikeRespository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ExerciseLikeRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore) :
    ExerciseLikeRespository {

    override suspend fun addExerciseLike(
        userId: String,
        exerciseId: String,
        like: Boolean
    ): Result<Unit> {

        return try {
            val docId = "$userId-$exerciseId"
            val likeData = mapOf(
                "userId" to userId,
                "exerciseId" to exerciseId,
                "like" to like
            )
            firestore.collection("exercise_likes")
                .document(docId)
                .set(likeData)
                .await()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error("Error adding Like: ${e.message}")
        }
    }

    override suspend fun getExerciseLikesByUserId(userId: String): Result<List<String>> {
        return try{
            val snapshot = firestore.collection("exercise_likes")
                .whereEqualTo("userId", userId)
                .get()
                .await()
            val exercises = snapshot.documents.mapNotNull { doc ->
                doc.getString("exerciseId")
            }
            Result.success(exercises)

        } catch (e: Exception) {
            Result.Error("Error getting Exercises by Likes: ${e.message}")
        }
    }
}