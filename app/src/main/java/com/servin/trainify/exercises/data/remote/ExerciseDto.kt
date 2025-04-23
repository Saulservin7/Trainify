package com.servin.trainify.exercises.data.remote

import com.servin.trainify.exercises.data.model.Exercise

data class ExerciseDto(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val mediaUrls: List<String>? = null,
    val objective: String? = null,
    val sportContext: String? = null,

) {
    fun toDomain() = Exercise(
       id=id ?:"" ,
        title = title ?: "",
        description = description ?: "",
        mediaUrls = mediaUrls ?: emptyList(),
        objective = objective ?: "",
        sportContext = sportContext ?: "",
    )

    companion object {
        fun fromDomain(exercise: Exercise): ExerciseDto = ExerciseDto(
            id = exercise.id,
            title = exercise.title,
            description = exercise.description,
            mediaUrls = exercise.mediaUrls,
            objective = exercise.objective,
            sportContext = exercise.sportContext
        )

    }

}
