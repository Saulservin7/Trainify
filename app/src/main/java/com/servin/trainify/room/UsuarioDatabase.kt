package com.servin.trainify.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.servin.trainify.ui.home.model.Exercise
import com.servin.trainify.ui.register.model.User

@Database(entities = [Exercise::class], version =1, exportSchema = false)

abstract class UsuarioDatabase: RoomDatabase(){
    abstract fun exerciseDao(): ExerciseDao
}