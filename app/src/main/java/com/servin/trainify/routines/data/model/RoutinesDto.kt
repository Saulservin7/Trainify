package com.servin.trainify.routines.data.model

import com.servin.trainify.exercises.data.remote.ExerciseDto
import com.servin.trainify.routines.domain.model.ExerciseDetails
import com.servin.trainify.routines.domain.model.Routines

data class RoutinesDto(
    val id: String,
    val title: String,
    val description: String,
    val exercises: List<ExerciseDto>,
    val exerciseDetails: List<ExerciseDetails>,
    val days: List<String>,
    val isPublic: Boolean = false,
) {

    fun toDomain(): Routines {
        return Routines(
            id = id,
            title = title,
            description = description,
            exercises = exercises.map { it.toDomain() },
            exerciseDetails = exerciseDetails,
            days = days,
            isPublic = isPublic
        )
    }
    companion object {
        fun fromDomain(routine: Routines): RoutinesDto {
            return RoutinesDto(
                id = routine.id,
                title = routine.title,
                description = routine.description,
                exercises = routine.exercises.map { ExerciseDto.fromDomain(it) },
                exerciseDetails = routine.exerciseDetails,
                days = routine.days,
                isPublic = routine.isPublic
            )
        }
    }
}