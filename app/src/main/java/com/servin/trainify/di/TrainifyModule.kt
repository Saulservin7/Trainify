package com.servin.trainify.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.servin.trainify.data.AuthRepositoryImpl
import com.servin.trainify.domain.usecase.AuthUseCase
import com.servin.trainify.domain.usecase.DeleteExercise
import com.servin.trainify.domain.usecase.ExerciseUseCase
import com.servin.trainify.domain.usecase.GetCurrentUser
import com.servin.trainify.domain.usecase.GetExercisebyId
import com.servin.trainify.domain.usecase.GetExercises
import com.servin.trainify.domain.usecase.InsertExercise
import com.servin.trainify.domain.usecase.LoginUseCase
import com.servin.trainify.domain.usecase.LogoutUseCase
import com.servin.trainify.domain.usecase.RegisterUseCase
import com.servin.trainify.domain.usecase.UpdateExercise
import com.servin.trainify.repository.AuthRepository
import com.servin.trainify.room.ExerciseRepository
import com.servin.trainify.room.UsuarioDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object TrainifyModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): UsuarioDatabase {
        return Room.databaseBuilder(context, UsuarioDatabase::class.java, "trainifyDB").build()
    }




    @Provides
    fun provideExerciseDao(database: UsuarioDatabase) = database.exerciseDao()

    @Provides
    fun provideUseCase(repository: ExerciseRepository)=ExerciseUseCase(
        insertExercise = InsertExercise(repository),
        getExercises = GetExercises(repository),
        getExercisebyId = GetExercisebyId(repository),
        updateExercise = UpdateExercise(repository),
        deleteExercise = DeleteExercise(repository)
    )

    @Provides
    fun provideFirebaseAuth()=FirebaseAuth.getInstance()

    @Provides
    fun provideAuthRepository(impl:AuthRepositoryImpl):AuthRepository=impl

    @Provides
    fun provideAuthCase(repository: AuthRepository)= AuthUseCase(
        getCurrentUser = GetCurrentUser(repository),
        login = LoginUseCase(repository),
        logout = LogoutUseCase(repository),
        register = RegisterUseCase(repository)
    )
}