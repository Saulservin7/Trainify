package com.servin.trainify.ui.login.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel(){


    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()

    val password : LiveData<String> = _password

    private val _loginState = MutableLiveData<Boolean>()
    val loginState: LiveData<Boolean> = _loginState

    fun onLoginChanged(email: String, password: String) {

        _email.value = email
        _password.value = password
        _loginState.value = isValidEmail(email) && isValidPassword(password)

    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }

    private fun isValidEmail(email: String): Boolean {
        return email.contains("@")
    }

    fun onLoginSelected() {


    }


}