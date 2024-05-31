package com.servin.trainify.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.servin.trainify.domain.usecase.ExerciseUseCase
import com.servin.trainify.room.ExerciseRepository
import com.servin.trainify.ui.home.model.Exercise
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ExerciseViewModel @Inject constructor(private val useCase: ExerciseUseCase): ViewModel(){

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> = _title

    private val _description = MutableLiveData<String>()
    val description: LiveData<String> = _description

    private val _image = MutableLiveData<Bitmap?>()
    val image: LiveData<Bitmap?> = _image

    fun onExerciseChanged(title: String, description: String, image: Bitmap?) {
        _title.value = title
        _description.value = description
        _image.value = image
    }


    fun addExercise() = viewModelScope.launch{
        val titleValue = _title.value ?: return@launch
        val descriptionValue = _description.value ?: return@launch
        useCase.insertExercise(Exercise(title= titleValue, description = descriptionValue))
    }




}