package com.servin.trainify.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseUser
import com.servin.trainify.domain.usecase.AuthUseCase
import com.servin.trainify.model.DataResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authUseCase: AuthUseCase) : ViewModel(){


    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()

    val password : LiveData<String> = _password

    private val _loginState = MutableLiveData<Boolean>()
    val loginState: LiveData<Boolean> = _loginState





    var stateLogin by mutableStateOf<DataResponse<FirebaseUser>?>(null)

    init {
        viewModelScope.launch {
            val currentUser = authUseCase.getCurrentUser()
            if (currentUser != null){
                stateLogin = DataResponse.Success(currentUser)
            }
        }
    }



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

    fun onLoginSelected()=viewModelScope.launch{
        stateLogin=DataResponse.Loading
        stateLogin = authUseCase.login(email.value!!,password.value!!)



    }


}