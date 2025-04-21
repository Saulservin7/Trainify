package com.servin.trainify.profile.data.remote

import com.servin.trainify.auth.domain.model.User

data class UserDto(
    val id: String? = null,
    val name: String? = null,
    val email: String? = null,
    val age: String? = null,
    val height: String? = null,
    val weight: String? = null,
    val gender: String? = null,
) {
    fun toDomain(): User = User(
        id = id ?: "",
        name = name ?: "",
        email = email ?: "",
        age = age,
        height = height,
        weight = weight,
        gender = gender
    )

    companion object{
        fun fromDomain (user: User):UserDto=UserDto(
            id = user.id,
            name = user.name,
            email = user.email,
            age = user.age,
            height = user.height,
            weight = user.weight,
            gender = user.gender
        )
    }
}