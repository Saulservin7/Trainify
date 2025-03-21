package com.servin.trainify.di

import com.servin.trainify.auth.domain.repository.AuthRepository
import com.servin.trainify.auth.domain.usecase.LoginUseCase
import com.servin.trainify.auth.domain.usecase.LogoutUseCase
import com.servin.trainify.auth.domain.usecase.RegisterUseCase
import com.servin.trainify.data.model.Exercise
import com.servin.trainify.domain.repository.ExerciseRepository
import com.servin.trainify.domain.usecase.AddExerciseUseCase
import com.servin.trainify.domain.usecase.GetExercisesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideRegisterUseCase(authRepository: AuthRepository): RegisterUseCase {
        return RegisterUseCase(authRepository)
    }


    @Provides
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase {
        return LoginUseCase(authRepository)
    }

    @Provides
    fun provideLogoutUseCase(authRepository: AuthRepository): LogoutUseCase {
        return LogoutUseCase(authRepository)
    }

    @Provides
    fun provideAddExerciseUseCase(exerciseRepository: ExerciseRepository): AddExerciseUseCase {
        return AddExerciseUseCase(exerciseRepository)
    }

    @Provides
    fun provideGetExerciseUseCase(exerciseRepository: ExerciseRepository): GetExercisesUseCase {
        return GetExercisesUseCase(exerciseRepository)
    }

}