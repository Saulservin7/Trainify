package com.servin.trainify.domain.repository


import com.servin.trainify.domain.model.ExerciseCategory

class CategoryRepository {

    fun getExerciseCategories(): List<ExerciseCategory> {
        return ExerciseCategory.allCategories
    }

}