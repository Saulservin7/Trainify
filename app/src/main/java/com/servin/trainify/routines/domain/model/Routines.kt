package com.servin.trainify.routines.domain.model

import com.servin.trainify.exercises.data.model.Exercise

data class Routines(
    val id: String,
    val title: String,
    val description:String,
    val exercises: List<Exercise>,
    val exerciseDetails: List<ExerciseDetails>,
    val days: List<String>,
    val isPublic: Boolean = false,
)