package com.servin.trainify.auth.domain.model

import org.junit.Assert.*
import org.junit.Test

class ResultTest{
  @Test

    fun `success result should contain the correct data`() {
        // Given
        val expectedData = "Sucess Data"
        val result = Result.Success(expectedData)


        // Then
        assertEquals(expectedData, result.data)
    }

    @Test
    fun `error result should contain the correct message`() {
        // Given
        val expectedMessage = "Error Message"
        val result = Result.Error<String>(expectedMessage)

        // Then
        assertEquals(expectedMessage, result.message)
    }
 }