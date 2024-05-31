package com.servin.trainify.room

import com.servin.trainify.ui.home.model.Exercise
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExerciseRepository @Inject constructor(private val dao: ExerciseDao) : ExerciseDao {
    override fun getAllExercises(): Flow<List<Exercise>> {
        return dao.getAllExercises()
    }

    override fun getExerciseById(id: Int): Flow<Exercise> {
        return dao.getExerciseById(id)
    }


    override suspend fun insertExercise(exercise: Exercise) = dao.insertExercise(exercise)


    override suspend fun updateExercise(exercise: Exercise) = dao.updateExercise(exercise)


    override suspend fun deleteExercise(exercise: Exercise) = dao.deleteExercise(exercise)


}