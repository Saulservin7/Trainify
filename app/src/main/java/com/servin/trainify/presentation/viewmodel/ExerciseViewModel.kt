package com.servin.trainify.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.servin.trainify.data.model.Exercise
import com.servin.trainify.domain.model.Result
import com.servin.trainify.domain.usecase.AddExerciseUseCase
import com.servin.trainify.domain.usecase.GetExercisesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.servin.trainify.domain.model.onError
import com.servin.trainify.domain.model.onSuccess




@HiltViewModel
class ExerciseViewModel @Inject constructor(
     private val addExerciseUseCase: AddExerciseUseCase,
    private val getExercisesUseCase: GetExercisesUseCase
):ViewModel() {

    private val _state = MutableStateFlow<Result<List<Exercise>>>(Result.idle())
    val state: StateFlow<Result<List<Exercise>>> = _state

    private val _stateExercise = MutableStateFlow<Result<Unit>>(Result.idle())
    val stateExercise: StateFlow<Result<Unit>> = _stateExercise

    init {
        loadExercises()
    }



    fun loadExercises() {
        viewModelScope.launch {
            _state.value = Result.loading()
            _state.value = getExercisesUseCase()
                .onSuccess { Log.d("VM", "Ejercicios: ${it.size}") }
                .onError { Log.e("VM", "Error: $it") }
        }
    }

    fun addExercises(exercise: Exercise){
        viewModelScope.launch {
            _stateExercise.value = Result.loading()
            _stateExercise.value = addExerciseUseCase(exercise)
                .onSuccess { Log.d("VM", "Ejercicio añadido") }
                .onError { Log.e("VM", "Error: $it") }

        }
    }




}