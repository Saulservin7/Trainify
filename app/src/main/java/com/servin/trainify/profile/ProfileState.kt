package com.servin.trainify.profile

import com.servin.trainify.auth.domain.model.User

sealed class ProfileState {

   object Loading : ProfileState()
    data class Data(val user: User) : ProfileState()
     object MissingData : ProfileState()
}