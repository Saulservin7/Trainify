package com.servin.trainify.di

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SelectedExercisesRepository @Inject constructor() {

    private val _selectedExercisesIds = MutableStateFlow<List<String>>(emptyList())
    val selectedExercisesIds = _selectedExercisesIds



    private val _triggerExerciseLoad = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val triggerExerciseLoad = _triggerExerciseLoad

    fun triggerExerciseSync() {
        _triggerExerciseLoad.tryEmit(Unit)
    }

    fun toggleExerciseSelection(id: String) {
        val current = _selectedExercisesIds.value.toMutableList()
        if (current.contains(id)) {
            current.remove(id)
        } else {
            current.add(id)
        }
        _selectedExercisesIds.value = current
    }

    fun getCurrentSelectedExercises(): List<String> {
        return _selectedExercisesIds.value
    }




}