package com.servin.trainify.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.servin.trainify.auth.domain.model.User
import com.servin.trainify.auth.domain.repository.AuthRepository
import com.servin.trainify.exercises.domain.model.ExerciseCategory
import com.servin.trainify.exercises.domain.repository.CategoryRepository
import com.servin.trainify.exercises.usecase.GetUserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val authRepository: AuthRepository,
    private val getUserData: GetUserData
) : ViewModel() {

    private val _categories = MutableStateFlow<List<ExerciseCategory>>(emptyList())
    val categories: StateFlow<List<ExerciseCategory>> = _categories.asStateFlow()

    private val _userData = MutableStateFlow<User?>(null)
    val userData: StateFlow<User?> = _userData.asStateFlow()

    init {
        loadCategories()
        loadUserData()
    }

    private fun loadCategories() {
        _categories.value = categoryRepository.getExerciseCategories()
    }

    private fun loadUserData(){
        viewModelScope.launch {
            _userData.value = getUserData()
        }
    }
}