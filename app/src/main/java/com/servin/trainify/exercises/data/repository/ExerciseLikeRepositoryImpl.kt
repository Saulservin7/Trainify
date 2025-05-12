package com.servin.trainify.exercises.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.servin.trainify.exercises.data.model.ExerciseLikes
import com.servin.trainify.exercises.data.remote.ExerciseDto
import com.servin.trainify.exercises.data.remote.ExerciseLikeDto
import com.servin.trainify.exercises.domain.model.Result
import com.servin.trainify.exercises.domain.repository.ExerciseLikeRespository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ExerciseLikeRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore) :
    ExerciseLikeRespository {

    override suspend fun addExerciseLike(exerciseLike: ExerciseLikes): Result<Unit> {
        return try {
            val docId = "${exerciseLike.userId}-${exerciseLike.exerciseId}"
            val docRef = firestore.collection("exercise_likes").document(docId)
            val snapshot = docRef.get().await()

            if (snapshot.exists()) {
                docRef.update("like", exerciseLike.like).await()
            } else {
                val likeData = mapOf(
                    "userId" to exerciseLike.userId,
                    "exerciseId" to exerciseLike.exerciseId,
                    "like" to exerciseLike.like,
                    "rating" to exerciseLike.rating
                )
                docRef.set(likeData).await()
            }

            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error("Error adding Like: ${e.message}")
        }
    }

    override suspend fun addRating(exerciseLike: ExerciseLikes): Result<Unit> {
        return try {
            val docId = "${exerciseLike.userId}-${exerciseLike.exerciseId}"
            val docRef = firestore.collection("exercise_likes").document(docId)
            val exerciseRef = firestore.collection("exercises").document(exerciseLike.exerciseId)
            val exerciseSnapshot = exerciseRef.get().await()
            val snapshot = docRef.get().await()
            val ratingCount = exerciseSnapshot.getLong("ratingCount") ?: 0L
            val newRatingCount = ratingCount + 1
            val average = exerciseSnapshot.getDouble("average") ?: 0.0
            val newAverage = (average * ratingCount + exerciseLike.rating) / newRatingCount
            if (snapshot.exists()) {
                val currentRating = snapshot.getDouble("rating") ?: 0.0
                val safeRatingCount = if (ratingCount == 0L) 1L else ratingCount
                exerciseRef.update(
                    mapOf(
                        "average" to (average * ratingCount - currentRating + exerciseLike.rating) / safeRatingCount
                    )
                ).await()

                docRef.update("rating", exerciseLike.rating).await()
            } else {
                val dto = ExerciseLikeDto.fromDomain(exerciseLike)
                docRef.set(dto).await()

                exerciseRef.set(
                    mapOf(
                        "ratingCount" to newRatingCount,
                        "average" to newAverage
                    ),
                    SetOptions.merge()
                ).await()
            }

            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error("Error adding Rating: ${e.message}")
        }

    }

    override suspend fun getExerciseLikesByUserId(userId: String): Result<List<String>> {
        return try {
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


    override suspend fun getExerciseLike(
        userId: String,
        exerciseId: String
    ): Result<ExerciseLikes> {
        return try {
            val docId = "$userId-$exerciseId"
            val snapshot = firestore.collection("exercise_likes").document(docId).get().await()

            if (snapshot.exists()) {
                val exerciseLike = snapshot.toObject(ExerciseLikeDto::class.java)?.toDomain()
                Result.Success(exerciseLike!!)
            } else {
                Result.Error("Exercise Like not found")
            }
        } catch (e: Exception) {
            Result.Error("Error getting Exercise Like: ${e.message}")
        }
    }
}