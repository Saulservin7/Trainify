package com.servin.trainify.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.servin.trainify.auth.data.AuthRepositoryImpl
import com.servin.trainify.auth.domain.repository.AuthRepository

import com.servin.trainify.data.repository.ExerciseRepositoryImpl
import com.servin.trainify.domain.repository.CategoryRepository
import com.servin.trainify.domain.repository.ExerciseRepository
import com.servin.trainify.navigation.NavigationManager

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    fun providesFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    fun provideAuthRepository(auth: FirebaseAuth,firestore: FirebaseFirestore): AuthRepository {
        return AuthRepositoryImpl(auth,firestore) // Usa tu implementación
    }

    @Provides
    fun provideExerciseRepository(firestore: FirebaseFirestore): ExerciseRepository {
        return ExerciseRepositoryImpl(firestore)
    }


    @Provides
    @Singleton
    fun provideCategoryRepository(): CategoryRepository = CategoryRepository()


    @Provides
    @Singleton
    fun provideNavigationManager(): NavigationManager = NavigationManager()

}


