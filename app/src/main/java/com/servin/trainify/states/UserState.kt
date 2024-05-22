package com.servin.trainify.states

import com.servin.trainify.ui.register.model.User

data class UserState (
    val listUser : List<User> = emptyList()
)