package com.servin.trainify.auth.domain.usecase

import com.servin.trainify.auth.domain.model.User
import com.servin.trainify.auth.domain.repository.AuthRepository
import com.servin.trainify.auth.domain.model.Result
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class RegisterUseCaseTest{
  //Mock del repositorio

  private val authRepository = mockk<AuthRepository>()
  private val registerUseCase = RegisterUseCase(authRepository)

  @Test
  fun `invoke should call repository register with correct params`() = runTest {
    // Arrange
    val email = "test@example.com"
    val password = "password123"
    val name = "Test User"
    val expectedUser = User("1", name, email) // Datos esperados
    val expectedResult = Result.Success(expectedUser)

    coEvery { authRepository.register(email, password, name) } returns expectedResult


    val result = registerUseCase(email, password, name)

    assertTrue(result is Result.Success)
    assertEquals(expectedUser, (result as Result.Success).data)


  }

  @Test

  fun `invoke should return error result when repository register fails`()= runTest {
    //Arrange
    val email = "testMail"
    val password = "password123"
    val name = "Test User"
    val expectedErrorMessage = "Registration failed"
    val expectedResult = Result.Error<User>(expectedErrorMessage)

    coEvery { authRepository.register(email, password, name) } returns expectedResult

    val result = registerUseCase(email, password, name)
    assertTrue(result is Result.Error)
    assertEquals(expectedErrorMessage, (result as Result.Error).message)
  }

    @Test
    fun `invoke should return error result when email is invalid`() = runTest {
        // Arrange
        val email = "invalidEmail"
        val password = "password123"
        val name = "Test User"
        val expectedErrorMessage = "Invalid email format"
        val expectedResult = Result.Error<User>(expectedErrorMessage)

        coEvery { authRepository.register(email, password, name) } returns expectedResult

        // Act
        val result = registerUseCase(email, password, name)

        // Assert
        assertTrue(result is Result.Error)
        assertEquals(expectedErrorMessage, (result as Result.Error).message)
    }

 }