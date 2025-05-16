package com.servin.trainify.routines.presentation.viewmodel

import android.text.BoringLayout
import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.servin.trainify.auth.UserSessionManager
import com.servin.trainify.di.SelectedExercisesRepository
import com.servin.trainify.exercises.data.model.Exercise
import com.servin.trainify.exercises.presentation.viewmodel.ExerciseViewModel.FormFieldState
import com.servin.trainify.routines.domain.model.ExercisesDetailState
import com.servin.trainify.routines.usecase.AddRoutineUseCase
import com.servin.trainify.routines.usecase.GetRoutinesUseCase
import com.servin.trainify.routines.usecase.LoadExercisesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Thread.State
import javax.inject.Inject


@HiltViewModel
class RoutinesViewModel @Inject constructor(
    private val getRoutinesUseCase: GetRoutinesUseCase,
    private val addRoutineUseCase: AddRoutineUseCase,
    private val loadExercisesUseCase: LoadExercisesUseCase,
    userSessionManager: UserSessionManager,
    selectedExercisesRepository: SelectedExercisesRepository
) : ViewModel() {

    val selectedExercises = selectedExercisesRepository.selectedExercisesIds

    private val userUid = userSessionManager.getCurrentUserUid()

    init {
        viewModelScope.launch {
            selectedExercisesRepository.triggerExerciseLoad.collect {
                val selected = selectedExercisesRepository.getCurrentSelectedExercises()
                loadExercises(selected)
            }
        }
    }


    private val _title = MutableStateFlow(FormFieldState(""))
    val title: StateFlow<FormFieldState<String>> = _title

    private val _description = MutableStateFlow(FormFieldState(""))
    val description: StateFlow<FormFieldState<String>> = _description

    private val _seriesList = MutableStateFlow<List<String>>(emptyList())
    val seriesList: StateFlow<List<String>> = _seriesList

    private val _repetitions = MutableStateFlow("")
    val repetitions: StateFlow<String> = _repetitions
    private val _restTime = MutableStateFlow("")
    val restTime: StateFlow<String> = _restTime
    private val _weight = MutableStateFlow("")
    val weight: StateFlow<String> = _weight

    private val _isWeight = MutableStateFlow(false)
    val isWeight: StateFlow<Boolean> = _isWeight




    private val _exercisesList = MutableStateFlow<List<Exercise>>(emptyList())
    val exercisesList: StateFlow<List<Exercise>> = _exercisesList

    private val _isPublic = MutableStateFlow(false)
    val isPublic: StateFlow<Boolean> = _isPublic

    private val _exercisesDetails = MutableStateFlow<List<ExercisesDetailState>>(emptyList())
    val exercisesDetails: StateFlow<List<ExercisesDetailState>> = _exercisesDetails

    fun setTitle(title: String) {
        val isError = !title.matches(Regex("^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ\\s]+$"))
        _title.value = FormFieldState(title, isError)
    }

    fun setDescription(description: String) {
        val isError = !description.matches(Regex("^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ\\s]+$"))
        _description.value = FormFieldState(description, isError)
    }

    fun setSeries(index: Int, series: String) {
        val currentList = _seriesList.value.toMutableList()
        if (index in currentList.indices) {
            currentList[index] = series
            _seriesList.value = currentList
        }
    }
    fun setRepetitions(repetitions: String) {
        _repetitions.value = repetitions
    }
    fun setRestTime(restTime: String) {
        _restTime.value = restTime
    }
    fun setWeight(weight: String) {
        _weight.value = weight
    }
    fun setIsWeight(isWeight: Boolean) {
        _isWeight.value = isWeight
    }

    fun updateExercisesList(updatedDetail: ExercisesDetailState) {
        _exercisesDetails.value = _exercisesDetails.value.map {
            if (it.exerciseId == updatedDetail.exerciseId) updatedDetail else it
        }
    }

    fun getExercisesByUserSelect(exercisesList: List<String>) {
        viewModelScope.launch {
            val result = getRoutinesUseCase()
        }
    }

    private fun loadExercises(selectedExercises: List<String>) {
        viewModelScope.launch {
            val result = loadExercisesUseCase(selectedExercises)
            if (result is com.servin.trainify.exercises.domain.model.Result.Success) {
                _exercisesList.value = result.data
                val oldSeries = _seriesList.value
                val newSize = selectedExercises.size

                _seriesList.value = if (newSize > oldSeries.size) {
                    oldSeries + List(newSize - oldSeries.size) { "" }
                } else {
                    oldSeries.take(newSize)
                }
                Log.d("RoutinesViewModel", "Ejercicios cargados: ${result.data}")
            } else if (result is com.servin.trainify.exercises.domain.model.Result.Error) {
                // Puedes agregar manejo de error aquí si tienes UI que lo muestra
                println("Error cargando ejercicios: ${result.message}")
            }
        }
    }


}