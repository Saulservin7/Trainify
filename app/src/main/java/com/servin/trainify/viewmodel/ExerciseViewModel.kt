package com.servin.trainify.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ExerciseViewModel @Inject constructor(): ViewModel(){

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
}