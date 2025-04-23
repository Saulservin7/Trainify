package com.servin.trainify.exercises.presentation.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
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
import androidx.core.net.toUri
import com.servin.trainify.auth.UserSessionManager


@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val addExerciseUseCase: AddExerciseUseCase,
    private val userSessionManager: UserSessionManager

):ViewModel() {

    private val _state = MutableStateFlow<Result<List<Exercise>>>(Result.idle())
    val state: StateFlow<Result<List<Exercise>>> = _state

    private val _mediaList = MutableStateFlow<List<String>>(emptyList())
    val mediaList: StateFlow<List<String>> = _mediaList

    private var uid: String


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

    fun verifyData(exercise: Exercise, mediaUris: List<Uri>): Boolean {
        return (exercise.title.isEmpty() || exercise.description.isEmpty() || exercise.id.isEmpty() || mediaUris.isEmpty() || exercise.objective.isEmpty() || exercise.sportContext.isEmpty())
                || (_title.value.isError || _description.value.isError || _id.value.isError || _objective.value.isError || _sportContext.value.isError)
    }

    private fun getFileExtension(context: Context, uri: Uri): String? {
        return MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(context.contentResolver.getType(uri))
    }

    fun processMedia(context: Context): Pair<List<String>, List<String>> {
        val images = _mediaList.value.filter { uriString ->
            val uri = uriString.toUri()
            val extension = getFileExtension(context, uri)
            extension in setOf("jpg", "png", "jpeg")
        }

        val videos = _mediaList.value.filter { uriString ->
            val uri = uriString.toUri()
            val extension = getFileExtension(context, uri)
            extension in setOf("mp4", "mov")
        }

        return Pair(images, videos)
    }



    init {

         uid = userSessionManager.getCurrentUserUid()
    }





    fun addExercises(exercise: Exercise, mediaUris: List<String>, userUid: String) {
        viewModelScope.launch {
            _stateExercise.value = Result.loading()
            Log.d("Donas", "$mediaUris")

            // Llamada al use case con los parámetros necesarios
            _stateExercise.value = addExerciseUseCase(exercise, mediaUris, userUid = uid)
                .onSuccess {

                    Log.d("VM", "Ejercicio añadido")
                }
                .onError {
                    Log.e("VM", "Error: $it")
                }
        }
    }

    // Lista de medios (imágenes o videos)


    fun addMedia(uri: String) {
        _mediaList.value = _mediaList.value + uri
    }
}