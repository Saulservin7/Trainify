package com.servin.trainify.routines.domain.model

data class ExerciseDetails(
    val exerciseId: String,
    val sets: Int,
    val repetitions: Int,
    val restTime: Int,
    val isWeight: Boolean,
    val weight: Float?,
)