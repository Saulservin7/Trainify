package com.servin.trainify.routines.presentation.viewmodel

import android.text.BoringLayout
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import com.servin.trainify.auth.UserSessionManager
import com.servin.trainify.exercises.presentation.viewmodel.ExerciseViewModel.FormFieldState
import com.servin.trainify.routines.domain.model.ExercisesDetailState
import com.servin.trainify.routines.usecase.AddRoutineUseCase
import com.servin.trainify.routines.usecase.GetRoutinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.lang.Thread.State
import javax.inject.Inject


@HiltViewModel
class RoutinesViewModel @Inject constructor(
    private val getRoutinesUseCase: GetRoutinesUseCase,
    private val addRoutineUseCase: AddRoutineUseCase,
    userSessionManager: UserSessionManager
) : ViewModel() {

    private val userUid = userSessionManager.getCurrentUserUid()


    private val _title = MutableStateFlow(FormFieldState(""))
    val title: StateFlow<FormFieldState<String>> = _title

    private val _description = MutableStateFlow(FormFieldState(""))
    val description: StateFlow<FormFieldState<String>> = _description

    private val _seriesList = MutableStateFlow(List(5) { "" }) // ← crea una lista con 5 strings vacíos
    val seriesList: StateFlow<List<String>> = _seriesList

    private val _repetitions = MutableStateFlow("")
    val repetitions: StateFlow<String> = _repetitions
    private val _restTime = MutableStateFlow("")
    val restTime: StateFlow<String> = _restTime
    private val _weight = MutableStateFlow("")
    val weight: StateFlow<String> = _weight

    private val _isWeight = MutableStateFlow(false)
    val isWeight: StateFlow<Boolean> = _isWeight




    private val _exercisesList = MutableStateFlow<List<String>>(emptyList())
    val exercisesList: StateFlow<List<String>> = _exercisesList

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


}