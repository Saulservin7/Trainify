package com.servin.trainify.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.servin.trainify.domain.usecase.AuthUseCase
import com.servin.trainify.model.DataResponse
import com.servin.trainify.ui.register.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel  @Inject constructor(private val authUseCase: AuthUseCase): ViewModel(){

    private val _firstName = MutableLiveData<String>()
    val firstName: MutableLiveData<String> = _firstName

    private val _lastName = MutableLiveData<String>()
    val lastName: MutableLiveData<String> = _lastName

    private val _email = MutableLiveData<String>()
    val email: MutableLiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: MutableLiveData<String> = _password

    private val _registerState = MutableLiveData<Boolean>()
    val registerState: MutableLiveData<Boolean> = _registerState

    var stateRegister by mutableStateOf<DataResponse<FirebaseUser>?>(null)



    fun onRegisterChanged(firstName: String, lastName: String, email: String, password: String) {
        _firstName.value = firstName
        _lastName.value = lastName
        _email.value = email
        _password.value = password
        _registerState.value = isValidEmail(email) && isValidPassword(password)
    }

     fun onRegisterSelected(email: String, password: String)=viewModelScope.launch {
        if (isValidEmail(email) && isValidPassword(password)) {
            stateRegister = DataResponse.Loading
            stateRegister = authUseCase.register(email,password)
        }
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }

    private fun isValidEmail(email: String): Boolean {
        return email.contains("@")
    }

}