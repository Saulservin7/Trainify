package com.servin.trainify.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.servin.trainify.room.UserDao
import com.servin.trainify.states.UserState
import com.servin.trainify.ui.register.model.User
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UsuariosViewModel(
    private val dao: UserDao
) : ViewModel() {
    var state by mutableStateOf(UserState())
        private set

    init {
        viewModelScope.launch {
            dao.getAllUsers().collectLatest { state = state.copy(listUser = it) }
        }

    }

    fun insertUser(user: User)= viewModelScope.launch {
        dao.insertUser(user)
    }

    fun updateUser(user: User)= viewModelScope.launch {
        dao.updateUser(user)
    }

    fun deleteUser(user: User)= viewModelScope.launch {
        dao.deleteUser(user)
    }




}