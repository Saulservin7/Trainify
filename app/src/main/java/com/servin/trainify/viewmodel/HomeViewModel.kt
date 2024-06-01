package com.servin.trainify.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.servin.trainify.domain.usecase.ExerciseUseCase
import com.servin.trainify.ui.home.model.Exercise
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: ExerciseUseCase): ViewModel(){

    fun getAllExercises() = useCase.getExercises()

    fun deleteExercise(exercise: Exercise){
        viewModelScope.launch {
            useCase.deleteExercise(exercise)

        }
    }


}