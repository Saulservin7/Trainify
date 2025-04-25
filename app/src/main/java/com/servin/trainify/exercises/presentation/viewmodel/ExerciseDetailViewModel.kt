package com.servin.trainify.exercises.presentation.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.servin.trainify.auth.UserSessionManager
import com.servin.trainify.exercises.data.model.Exercise
import com.servin.trainify.exercises.domain.model.Result
import com.servin.trainify.exercises.domain.model.onError
import com.servin.trainify.exercises.domain.model.onSuccess
import com.servin.trainify.exercises.usecase.AddExerciseLikeUseCase
import com.servin.trainify.exercises.usecase.GetExerciseByIdUseCase
import com.servin.trainify.exercises.usecase.GetExercisesByObjetiveUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ExerciseDetailViewModel @Inject constructor(
    private val getExerciseByIdUseCase: GetExerciseByIdUseCase,
    private val addExerciseLikeUseCase: AddExerciseLikeUseCase,
    private val userSessionManager: UserSessionManager
) : ViewModel() {

    private val _state = MutableStateFlow<Result<Exercise>>(Result.Idle)
    val state: StateFlow<Result<Exercise>> = _state

    private val userId = userSessionManager.getCurrentUserUid()

    private val _likeState = MutableStateFlow<Boolean>(false)
    val likeState: StateFlow<Boolean> = _likeState

    private val _ratingState = MutableStateFlow<Int>(0)
    val ratingState: StateFlow<Int> = _ratingState

    fun setLikeState(like: Boolean, exerciseId: String) {
        _likeState.value = like
        viewModelScope.launch {
            addExerciseLikeUseCase(
                userId = userId,
                exerciseId = exerciseId,
                like = like
            ).onSuccess {
                Log.d("ExerciseVM", "Like added successfully")
            }.onError {
                Log.e("ExerciseVM", "Error adding like: $it")
            }
        }
    }


    fun setRatingState(rating: Int) {
        _ratingState.value = rating
    }


    fun loadExercise(id: String) {
        viewModelScope.launch {
            _state.value = Result.loading()
            _state.value = getExerciseByIdUseCase(id)
                .onSuccess { Log.d("ExerciseVM", "Loaded: ${it.title}") }
                .onError { Log.e("ExerciseVM", "Error: $it") }
        }
    }
}