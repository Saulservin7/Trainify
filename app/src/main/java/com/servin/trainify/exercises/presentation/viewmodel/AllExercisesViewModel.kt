package com.servin.trainify.exercises.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.servin.trainify.exercises.data.model.Exercise
import com.servin.trainify.exercises.domain.model.Result
import com.servin.trainify.exercises.domain.model.onError
import com.servin.trainify.exercises.domain.model.onSuccess
import com.servin.trainify.exercises.usecase.GetExercisesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllExercisesViewModel @Inject constructor(
    private val getExercisesUseCase: GetExercisesUseCase
):ViewModel(){

    private val _state = MutableStateFlow<Result<List<Exercise>>>(Result.Idle)
    val state: StateFlow<Result<List<Exercise>>> = _state

    init {
        loadExercises()
    }

    private fun loadExercises() {
        viewModelScope.launch {
            _state.value = Result.loading()
            _state.value = getExercisesUseCase()
                .onSuccess { Log.d("VMsuccess", "Ejercicios: ${it.size}") }
                .onError { Log.e("VMerror", "Error: $it") }
        }
    }
}