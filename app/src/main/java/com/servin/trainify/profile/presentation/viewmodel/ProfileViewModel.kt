package com.servin.trainify.profile.presentation.viewmodel

import android.net.Uri
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
import com.servin.trainify.profile.usecase.UploadPhotoUseCase
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
    private val updatePhoto: UploadPhotoUseCase
) : ViewModel() {

    data class FormFieldState<T>(
        val value: T,
        val isError: Boolean = false
    )

    private val _photoUrl = MutableStateFlow("")
    val photoUrl: StateFlow<String> = _photoUrl

    private val _name = MutableStateFlow(FormFieldState(""))
    val name: StateFlow<FormFieldState<String>> = _name

    private val _age = MutableStateFlow(FormFieldState(""))
    val age: StateFlow<FormFieldState<String>> = _age

    private val _height = MutableStateFlow(FormFieldState(""))
    val height: StateFlow<FormFieldState<String>> = _height

    private val _weight = MutableStateFlow(FormFieldState(""))
    val weight: StateFlow<FormFieldState<String>> = _weight

    private val _gender = MutableStateFlow(FormFieldState(""))
    val gender: StateFlow<FormFieldState<String>> = _gender


    var state by mutableStateOf<ProfileState>(ProfileState.Loading)
        private set

    private val _isCompleteData = MutableStateFlow(false)
    val isCompleteData: StateFlow<Boolean> = _isCompleteData

    fun setName(name: String) {
        val isError = !name.matches(Regex("^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ\\s]+$"))
        _name.value = FormFieldState(name, isError)
    }

    fun setAge(age: String) {
        val isError = !age.matches(Regex("^[0-9]{1,3}$"))
        _age.value = FormFieldState(age, isError)
    }

    fun setHeight(height: String) {
        val isError = !height.matches(Regex("^[0-9]{1,3}$"))
        _height.value = FormFieldState(height, isError)
    }

    fun setWeight(weight: String) {
        val isError = !weight.matches(Regex("^[0-9]{1,3}$"))
        _weight.value = FormFieldState(weight, isError)
    }

    fun setGender(gender: String) {
        val isError = !gender.matches(Regex("^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ\\s]+$"))
        _gender.value = FormFieldState(gender, isError)
    }

    private fun verifyData(user: User): Boolean {
        return (user.name.isEmpty() || user.age.isNullOrEmpty() || user.height.isNullOrEmpty() || user.weight.isNullOrEmpty() || user.gender.isNullOrEmpty())
                || (name.value.isError || age.value.isError || weight.value.isError)
    }

    init {
        viewModelScope.launch {
            getUserProfile()
        }
    }

    suspend fun getUserProfile() {
        val user = getUserProfileUseCase()
        state = if (user != null) {
            if (user.photoUrl != null) {
                _photoUrl.value = user.photoUrl
            }
            ProfileState.Data(user)
        } else {
            ProfileState.MissingData
        }
        if (state is ProfileState.Data) {
            _isCompleteData.value = !verifyData((state as ProfileState.Data).user)
        }
    }

    fun uploadPhoto(uri: Uri) {
        viewModelScope.launch {
            val url = updatePhoto(uri)
            _photoUrl.value = url.orEmpty()

            val user = (state as? ProfileState.Data)?.user
            if (user != null && url != null) {
                val updatedUser = user.copy(photoUrl = url)
                updateUserProfileUseCase(updatedUser)
                state = ProfileState.Data(updatedUser)
            }
        }
    }

    fun EnabledButton(): Boolean {
        return name.value.value.isNotEmpty() &&
                !name.value.isError &&
                age.value.value.isNotEmpty() &&
                !age.value.isError &&
                height.value.value.isNotEmpty() &&
                !height.value.isError &&
                weight.value.value.isNotEmpty() &&
                !weight.value.isError &&
                gender.value.value.isNotEmpty()
    }

    fun updateUserProfile(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            val updatedUser = user.copy(
                name = name.value.value,
                age = age.value.value,
                height = height.value.value,
                weight = weight.value.value,
                gender = gender.value.value,
                photoUrl = photoUrl.value
            )
            updateUserProfileUseCase(updatedUser)
            state = ProfileState.Data(updatedUser)
            _isCompleteData.value = !verifyData(updatedUser)
        }
    }

}