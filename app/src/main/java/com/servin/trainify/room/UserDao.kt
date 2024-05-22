package com.servin.trainify.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.servin.trainify.ui.register.model.User
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {

    @Query("SELECT * FROM usuarios")
     fun  getAllUsers(): Flow<List<User>>

    @Query ("SELECT * FROM usuarios WHERE id= :id")
    fun getUserById(id: Int): Flow<User>

    @Insert
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)


}