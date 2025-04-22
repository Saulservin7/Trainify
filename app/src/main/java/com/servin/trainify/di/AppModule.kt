package com.servin.trainify.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.servin.trainify.auth.data.AuthRepositoryImpl
import com.servin.trainify.auth.domain.repository.AuthRepository

import com.servin.trainify.exercises.data.repository.ExerciseRepositoryImpl
import com.servin.trainify.exercises.domain.repository.CategoryRepository
import com.servin.trainify.exercises.domain.repository.ExerciseRepository
import com.servin.trainify.navigation.NavigationManager
import com.servin.trainify.profile.data.ProfileRepositoryImp
import com.servin.trainify.profile.domain.repository.ProfileRepository

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
    fun provideAuthRepository(auth: FirebaseAuth, firestore: FirebaseFirestore): AuthRepository {
        return AuthRepositoryImpl(auth, firestore) // Usa tu implementación
    }

    @Provides
    fun provideExerciseRepository(firestore: FirebaseFirestore,storage: FirebaseStorage): ExerciseRepository {
        return ExerciseRepositoryImpl(firestore,storage)
    }

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }


    @Provides
    @Singleton
    fun provideCategoryRepository(

    ): CategoryRepository = CategoryRepository()

    @Provides
    @Singleton
    fun provideProfileRepository(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore,
        storage: FirebaseStorage
    ): ProfileRepository {
        return ProfileRepositoryImp(auth, firestore,storage)
    }


    @Provides
    @Singleton
    fun provideNavigationManager(): NavigationManager = NavigationManager()

}


