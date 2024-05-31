package com.servin.trainify.viewmodel

import androidx.lifecycle.ViewModel
import com.servin.trainify.domain.usecase.ExerciseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: ExerciseUseCase): ViewModel(){

    fun getAllExercises() = useCase.getExercises()


}