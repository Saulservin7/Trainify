package com.servin.trainify.ui.register.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "nombre")
    val username: String,
    @ColumnInfo(name = "apellido")
    val userlastname: String,
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name="contraseña")
    val password: String

)
