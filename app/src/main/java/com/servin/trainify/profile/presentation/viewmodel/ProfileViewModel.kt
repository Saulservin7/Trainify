package com.servin.trainify.profile.presentation.viewmodel

import androidx.collection.emptyLongSet
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.servin.trainify.auth.domain.model.User
import com.servin.trainify.profile.ProfileState
import com.servin.trainify.profile.domain.repository.ProfileRepository
import com.servin.trainify.profile.usecase.GetUserProfile
import com.servin.trainify.profile.usecase.UpdateUserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfile,
    private val updateUserProfileUseCase: UpdateUserProfile,
) : ViewModel() {

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _age = MutableStateFlow("")
    val age: StateFlow<String> = _age

    private val _height = MutableStateFlow("")
    val height: StateFlow<String> = _height

    private val _weight = MutableStateFlow("")
    val weight: StateFlow<String> = _weight

    private val _gender = MutableStateFlow("")
    val gender: StateFlow<String> = _gender

    private val _photoUrl = MutableStateFlow("")
    val photoUrl = _photoUrl

    private val _errorName = MutableStateFlow(false)
    val errorName: StateFlow<Boolean> = _errorName

    var state by mutableStateOf<ProfileState>(ProfileState.Loading)
        private set

    private val _isCompleteData = MutableStateFlow(false)
    val isCompleteData: StateFlow<Boolean> = _isCompleteData

    fun setName(name: String) {
        _name.value = name
        _errorName.value = !name.matches(Regex("^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ\\s]+$"))
    }

    fun setAge(age: String) {
        _age.value = age
    }

    fun setHeight(height: String) {
        _height.value = height
    }

    fun setWeight(weight: String) {
        _weight.value = weight
    }

    fun setGender(gender: String) {
        _gender.value = gender
    }

    private fun verifyData(user: User): Boolean {
        return user.age.isNullOrEmpty() || user.height.isNullOrEmpty() || user.weight.isNullOrEmpty() || user.gender.isNullOrEmpty()
    }

    init {
        viewModelScope.launch {
            getUserProfile()


        }
    }

    suspend fun getUserProfile(){
        val user = getUserProfileUseCase()
        state = if (user != null) {
            ProfileState.Data(user)
        } else {
            ProfileState.MissingData
        }
        if (state is ProfileState.Data) {
            _isCompleteData.value = !verifyData((state as ProfileState.Data).user)
        }
    }

    fun updateUserProfile(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            val updatedUser = user.copy(
                name = name.value,
                age = age.value,
                height = height.value,
                weight = weight.value,
                gender = gender.value
            )
            updateUserProfileUseCase(updatedUser)
            state = ProfileState.Data(updatedUser)
            _isCompleteData.value = !verifyData(updatedUser)
        }
    }

}