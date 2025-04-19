package com.servin.trainify.auth.presentation.viewmodel

import app.cash.turbine.test
import com.servin.trainify.auth.domain.model.Result
import com.servin.trainify.auth.domain.model.User
import com.servin.trainify.auth.domain.repository.AuthRepository
import com.servin.trainify.auth.domain.usecase.LoginUseCase
import com.servin.trainify.auth.domain.usecase.LogoutUseCase
import com.servin.trainify.auth.domain.usecase.RegisterUseCase
import io.mockk.coEvery
import io.mockk.mockk
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AuthViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private val mockAuthRepository = mockk<AuthRepository>()
    private val mockRegisterUseCase = mockk<RegisterUseCase>()
    private val mockLoginUseCase = mockk<LoginUseCase>()
    private val mockLogoutUseCase = mockk<LogoutUseCase>()

    private lateinit var viewModel: AuthViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        coEvery { mockAuthRepository.getCurrentUser() } returns null
        viewModel = AuthViewModel(
            mockAuthRepository,
            mockRegisterUseCase,
            mockLoginUseCase,
            mockLogoutUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `login success updates state to Authenticated`() = runTest {
        // 1. Mockear getCurrentUser() para este test específico (si es necesario)
        coEvery { mockAuthRepository.getCurrentUser() } returns null // Opcional

        // 2. Mockear el caso de uso de login
        val mockUser = User("123", "test@example.com", "Test User")
        coEvery { mockLoginUseCase(any(), any()) } returns Result.Success(mockUser)

        // 3. Ejecutar login
        viewModel.login("test@example.com", "password")

        // 4. Verificar estados
        viewModel.authstate.test {
            assertEquals(AuthState.Initial, awaitItem()) // Estado inicial
            assertEquals(AuthState.Loading, awaitItem()) // Durante el login
            assertEquals(AuthState.Authenticated(mockUser), awaitItem()) // Resultado exitoso
            cancelAndIgnoreRemainingEvents()
        }
    }
}