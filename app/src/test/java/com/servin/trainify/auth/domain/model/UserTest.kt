package com.servin.trainify.auth.domain.model

import org.junit.Assert.*
import org.junit.Test

class UserTest {

    @Test
    fun `user properties are correctly initialized`() {

        //Given

        val user = User("1", "Saul", "saulservin7@gmail.com")

        //Then

        assertEquals("1", user.id)
        assertEquals("Saul", user.name)
        assertEquals("saulservin7@gmail.com", user.email)

    }

    @Test
    fun `equals returns true for same properties`(){
        //Given
        val user1= User("1", "Saul", "saulservin7@gmail.com")
        val user2= User("1", "Saul", "saulservin7@gmail.com")

        assertTrue(user1==user2)
    }

    @Test
    fun `equals returns false for different properties`() {
        val user1 = User("1", "Saul", "saulservin7@gmail.com")
        val user2 = User("2", "Pablo", "pablo@example.com")
        assertFalse(user1 == user2) // ✔️ Deben ser diferentes
    }

    @Test
    fun `hashCode is same for equal objects`() {
        val user1 = User("1", "Saul", "saulservin7@gmail.com")
        val user2 = User("1", "Saul", "saulservin7@gmail.com")
        assertEquals(user1.hashCode(), user2.hashCode()) // ✔️ Mismo hashCode
    }

    @Test
    fun `hashcode differs for different objects`(){
        val user1 = User("1", "Saul", "saulservin7@gmail.com")
        val user2= User("2","Pablo","pablo@example.com")
    }
}