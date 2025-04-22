package com.servin.trainify.exercises.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.servin.trainify.exercises.data.model.Exercise
import com.servin.trainify.exercises.domain.model.Result
import com.servin.trainify.exercises.usecase.AddExerciseUseCase
import com.servin.trainify.exercises.usecase.GetExercisesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.servin.trainify.exercises.domain.model.onError
import com.servin.trainify.exercises.domain.model.onSuccess




@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val addExerciseUseCase: AddExerciseUseCase,
    private val getExercisesUseCase: GetExercisesUseCase
):ViewModel() {

    private val _state = MutableStateFlow<Result<List<Exercise>>>(Result.idle())
    val state: StateFlow<Result<List<Exercise>>> = _state


    data class FormFieldState<T>(
        val value: T,
        val isError: Boolean = false
    )

    private val _title = MutableStateFlow(FormFieldState(""))
    val title: StateFlow<FormFieldState<String>> = _title

    private val _description = MutableStateFlow(FormFieldState(""))
    val description: StateFlow<FormFieldState<String>> = _description

    private val _id = MutableStateFlow(FormFieldState(""))
    val id: StateFlow<FormFieldState<String>> = _id

    private val _imageURL = MutableStateFlow(FormFieldState(""))
    val imageURL: StateFlow<FormFieldState<String>> = _imageURL

    private val _videoURL = MutableStateFlow(FormFieldState(""))
    val videoURL: StateFlow<FormFieldState<String>> = _videoURL

    private val _objective = MutableStateFlow(FormFieldState(""))
    val objective: StateFlow<FormFieldState<String>> = _objective

    private val _sportContext = MutableStateFlow(FormFieldState(""))
    val sportContext: StateFlow<FormFieldState<String>> = _sportContext

    private val _stateExercise = MutableStateFlow<Result<Unit>>(Result.idle())
    val stateExercise: StateFlow<Result<Unit>> = _stateExercise

    fun setTitle(title: String) {
        val isError = !title.matches(Regex("^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ\\s]+$"))
        _title.value = FormFieldState(title, isError)
    }

    fun setDescription(description: String) {
        val isError = !description.matches(Regex("^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ\\s]+$"))
        _description.value = FormFieldState(description, isError)
    }

    fun setId(id: String) {
        val isError = !id.matches(Regex("^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ\\s]+$"))
        _id.value = FormFieldState(id, isError)
    }

    fun setImageURL(imageURL: String) {
        val isError = !imageURL.matches(Regex("^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ\\s]+$"))
        _imageURL.value = FormFieldState(imageURL, isError)
    }

    fun setVideoURL(videoURL: String) {
        val isError = !videoURL.matches(Regex("^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ\\s]+$"))
        _videoURL.value = FormFieldState(videoURL, isError)
    }

    fun setObjective(objective: String) {
        val isError = !objective.matches(Regex("^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ\\s]+$"))
        _objective.value = FormFieldState(objective, isError)
    }

    fun setSportContext(sportContext: String) {
        val isError = !sportContext.matches(Regex("^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ\\s]+$"))
        _sportContext.value = FormFieldState(sportContext, isError)
    }

    fun verifyData(exercise: Exercise): Boolean {
        return (exercise.title.isEmpty() || exercise.description.isEmpty() || exercise.id.isEmpty() || exercise.imageURL.isNullOrEmpty() || exercise.videoURL.isNullOrEmpty() || exercise.objective.isEmpty() || exercise.sportContext.isEmpty())
                || (_title.value.isError || _description.value.isError || _id.value.isError || _imageURL.value.isError || _videoURL.value.isError || _objective.value.isError || _sportContext.value.isError)
    }



    init {
        loadExercises()
    }



    private fun loadExercises() {
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

    // Lista de medios (imágenes o videos)
    private val _mediaList = MutableStateFlow<List<String>>(emptyList())
    val mediaList: StateFlow<List<String>> = _mediaList

    fun addMedia(uri: String) {
        _mediaList.value = _mediaList.value + uri
    }
}