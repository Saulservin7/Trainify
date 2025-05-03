package com.servin.trainify.exercises.presentation.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.servin.trainify.auth.UserSessionManager
import com.servin.trainify.exercises.data.model.Exercise
import com.servin.trainify.exercises.data.model.ExerciseLikes
import com.servin.trainify.exercises.domain.model.Result
import com.servin.trainify.exercises.domain.model.onError
import com.servin.trainify.exercises.domain.model.onSuccess
import com.servin.trainify.exercises.usecase.AddExerciseLikeUseCase
import com.servin.trainify.exercises.usecase.AddExerciseRatingUseCase
import com.servin.trainify.exercises.usecase.GetExerciseByIdUseCase
import com.servin.trainify.exercises.usecase.GetExerciseLikeUseCase
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
    private val addExerciseRatingUseCase: AddExerciseRatingUseCase,
    private val getExerciseLikeUseCase: GetExerciseLikeUseCase,
    userSessionManager: UserSessionManager
) : ViewModel() {

    private val _state = MutableStateFlow<Result<Exercise>>(Result.Idle)
    val state: StateFlow<Result<Exercise>> = _state

    private val userId = userSessionManager.getCurrentUserUid()

    private val _likeState = MutableStateFlow<Boolean>(false)
    val likeState: StateFlow<Boolean> = _likeState

    private val _ratingState = MutableStateFlow<Int>(-1)
    val ratingState: StateFlow<Int> = _ratingState

    fun setLikeState(like: Boolean, exerciseId: String) {
        _likeState.value = like

        viewModelScope.launch {
            val exerciseLike = ExerciseLikes(
                userId = userId,
                exerciseId = exerciseId,
                like = like,
                rating = -1
            )
            addExerciseLikeUseCase(exerciseLike)
                .onSuccess {
                    Log.d("ExerciseVM", "Like added successfully")
                }
                .onError {
                    Log.e("ExerciseVM", "Error adding like: $it")
                }
        }
    }


    fun setRatingState(rating: Int,exerciseId: String) {
        _ratingState.value = rating

        viewModelScope.launch {
            val exerciseLike = ExerciseLikes(
                userId = userId,
                exerciseId = exerciseId,
                like = false,
                rating = rating
            )
            addExerciseRatingUseCase(exerciseLike)
                .onSuccess {
                    Log.d("ExerciseVM", "Like added successfully")
                }
                .onError {
                    Log.e("ExerciseVM", "Error adding like: $it")
                }
        }
    }


    fun loadExercise(id: String) {
        viewModelScope.launch {
            _state.value = Result.loading()

            getExerciseByIdUseCase(id)
                .onSuccess { exercise ->
                    _state.value = Result.Success(exercise)
                    Log.d("ExerciseVM", "Loaded: ${exercise.title}")

                  viewModelScope.launch {
                      getExerciseLikeUseCase(userId, id)
                          .onSuccess { likeDto ->
                              _likeState.value = likeDto.like
                              _ratingState.value = likeDto.rating
                              Log.d(
                                  "ExerciseVM",
                                  "Like and rating loaded: ${likeDto.like}, ${likeDto.rating}"
                              )
                          }
                          .onError {
                              Log.d("ExerciseVM", "No existing like/rating found for user: $it")
                              _likeState.value = false
                              _ratingState.value = -1
                          }
                  }

                }
                .onError {
                    _state.value = Result.Error(it)
                    Log.e("ExerciseVM", "Error loading exercise: $it")
                }
        }
    }
}