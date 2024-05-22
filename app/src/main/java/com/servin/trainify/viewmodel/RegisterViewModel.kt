package com.servin.trainify.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel(){

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

    fun onRegisterChanged(firstName: String, lastName: String, email: String, password: String) {
        _firstName.value = firstName
        _lastName.value = lastName
        _email.value = email
        _password.value = password
        _registerState.value = isValidEmail(email) && isValidPassword(password)
    }

    fun onRegisterSelected() {

    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }

    private fun isValidEmail(email: String): Boolean {
        return email.contains("@")
    }

}