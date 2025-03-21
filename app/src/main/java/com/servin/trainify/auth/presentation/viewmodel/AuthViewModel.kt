package com.servin.trainify.auth.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.servin.trainify.auth.domain.model.Result
import com.servin.trainify.auth.domain.model.User
import com.servin.trainify.auth.domain.repository.AuthRepository
import com.servin.trainify.auth.domain.usecase.LoginUseCase
import com.servin.trainify.auth.domain.usecase.LogoutUseCase
import com.servin.trainify.auth.domain.usecase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val registerUseCase: RegisterUseCase,
    private val loginUseCase: LoginUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _errorName = MutableStateFlow(false)
    val errorName = _errorName
    private val _errorMail = MutableStateFlow(false)
    val errorMail = _errorMail
    private val _errorPassword = MutableStateFlow(false)
    val errorPassword = _errorPassword

    private val _errorConfirmPassword = MutableStateFlow(false)
    val errorConfirmPassword = _errorConfirmPassword

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _name = MutableStateFlow("")
    val name = _name

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword = _confirmPassword

    private val _authstate = MutableStateFlow<AuthState>(AuthState.Initial)
    val authstate: StateFlow<AuthState> = _authstate


    init {
        checkUser()
    }

    fun setName(name: String) {
        _name.value = name
        _errorName.value = !name.matches(Regex("^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ\\s]+$"))


    }

    fun setEmail(email: String) {
        _email.value = email
        _errorMail.value = !email.matches(Regex("^[a-zA-Z0-9+_.-]+@gmail.com+$"))
    }

    fun setPassword(password: String) {
        _password.value = password
        _errorPassword.value = !password.matches(Regex("^.{8,}$"))
    }

    fun confirmPassword(confirmPassword: String) {
        _confirmPassword.value = confirmPassword
        _errorConfirmPassword.value = confirmPassword != _password.value
    }

    fun checkUser() {
        viewModelScope.launch {
            authRepository.getCurrentUser()?.let { user ->
                _authstate.value = AuthState.Authenticated(user)
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
             logoutUseCase()
            _authstate.value = AuthState.Unauthenticated
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authstate.value = AuthState.Loading
            when (val result = loginUseCase(email, password)) {
                is Result.Success -> {
                    _authstate.value = AuthState.Authenticated(result.data)
                }

                is Result.Error<*> -> {
                    _authstate.value = AuthState.Error(result.message)
                }
            }
        }
    }

    fun register(email: String, password: String) {

        if (_errorName.value || _errorMail.value || _errorPassword.value || _errorConfirmPassword.value) {
            _authstate.value = AuthState.Error("Please correct the errors in the form")
            return
        }
        viewModelScope.launch {
            _authstate.value = AuthState.Loading
            when (val result = registerUseCase(email, password)) {
                is Result.Success -> {
                    _authstate.value = AuthState.Authenticated(result.data)
                    Log.d("AuthViewModel", "register: ${result.data}")
                }

                is Result.Error<*> -> Log.d("AuthViewModel", "register: ${result.message}")
            }
        }
    }
}


sealed class AuthState {
    object Initial : AuthState()
    object Loading : AuthState()
    object Unauthenticated : AuthState()
    data class Authenticated(val user: User) : AuthState()
    data class Error(val message: String) : AuthState()
}