package com.servin.trainify.di

import com.servin.trainify.auth.domain.repository.AuthRepository
import com.servin.trainify.auth.domain.usecase.LoginUseCase
import com.servin.trainify.auth.domain.usecase.LogoutUseCase
import com.servin.trainify.auth.domain.usecase.RegisterUseCase
import com.servin.trainify.exercises.domain.repository.ExerciseLikeRespository
import com.servin.trainify.exercises.domain.repository.ExerciseRepository
import com.servin.trainify.exercises.usecase.AddExerciseLikeUseCase
import com.servin.trainify.exercises.usecase.AddExerciseUseCase
import com.servin.trainify.exercises.usecase.GetExercisesUseCase
import com.servin.trainify.profile.domain.repository.ProfileRepository
import com.servin.trainify.profile.usecase.GetUserProfile
import com.servin.trainify.profile.usecase.UpdateUserProfile
import com.servin.trainify.profile.usecase.UploadPhotoUseCase
import com.servin.trainify.routines.domain.repository.RoutinesRepository
import com.servin.trainify.routines.usecase.AddRoutineUseCase
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

    @Provides
    fun provideGetProfileUseCase(profileRepository: ProfileRepository): GetUserProfile {
        return GetUserProfile(profileRepository)
    }

    @Provides
    fun provideUpdateProfileUseCase(profileRepository: ProfileRepository): UpdateUserProfile {
        return UpdateUserProfile(profileRepository)
    }

    @Provides
    fun provideUploadPhotoUseCase(profileRepository: ProfileRepository): UploadPhotoUseCase {
        return UploadPhotoUseCase(profileRepository)
    }

    @Provides
    fun provideExerciseLikeUseCase(exerciseLikeRespository: ExerciseLikeRespository): AddExerciseLikeUseCase {
        return AddExerciseLikeUseCase(exerciseLikeRespository)
    }

    @Provides
    fun provideAddExerciseRatingUseCase(exerciseLikeRespository: ExerciseLikeRespository): com.servin.trainify.exercises.usecase.AddExerciseRatingUseCase {
        return com.servin.trainify.exercises.usecase.AddExerciseRatingUseCase(exerciseLikeRespository)
    }

    @Provides
    fun provideGetExerciseLikeUseCase(exerciseLikeRespository: ExerciseLikeRespository): com.servin.trainify.exercises.usecase.GetExerciseLikeUseCase {
        return com.servin.trainify.exercises.usecase.GetExerciseLikeUseCase(exerciseLikeRespository)
    }

    @Provides
    fun provideAddRoutineUseCase(routineRepositoy:RoutinesRepository): AddRoutineUseCase{
        return  AddRoutineUseCase(routineRepositoy)
    }

    @Provides
    fun provideGetRoutineUseCase(routineRepositoy:RoutinesRepository): com.servin.trainify.routines.usecase.GetRoutinesUseCase{
        return  com.servin.trainify.routines.usecase.GetRoutinesUseCase(routineRepositoy)
    }

}