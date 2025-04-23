package com.servin.trainify.exercises.presentation.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.servin.trainify.exercises.data.model.Exercise
import com.servin.trainify.exercises.domain.model.Result
import com.servin.trainify.exercises.domain.model.onError
import com.servin.trainify.exercises.domain.model.onSuccess
import com.servin.trainify.exercises.usecase.GetExerciseByIdUseCase
import com.servin.trainify.exercises.usecase.GetExercisesByObjetiveUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ExerciseDetailViewModel @Inject constructor(
    private val getExerciseByIdUseCase: GetExerciseByIdUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<Result<Exercise>>(Result.Idle)
    val state: StateFlow<Result<Exercise>> = _state

    fun loadExercise(id: String) {
        viewModelScope.launch {
            _state.value = Result.loading()
            _state.value = getExerciseByIdUseCase(id)
                .onSuccess { Log.d("ExerciseVM", "Loaded: ${it.title}") }
                .onError { Log.e("ExerciseVM", "Error: $it") }
        }
    }
}