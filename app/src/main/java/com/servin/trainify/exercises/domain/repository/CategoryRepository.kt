package com.servin.trainify.exercises.domain.repository


import com.servin.trainify.exercises.domain.model.ExerciseCategory

class CategoryRepository {

    fun getExerciseCategories(): List<ExerciseCategory> {
        return ExerciseCategory.allCategories
    }

}