package com.servin.trainify.auth.data

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.servin.trainify.BaseTest
import com.servin.trainify.auth.domain.repository.AuthRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import com.servin.trainify.auth.domain.model.Result
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class AuthRepositoryImplTest:BaseTest(){
 private val mockAuth = mockk<FirebaseAuth>()
 private val mockFirestore = mockk<FirebaseFirestore>()

 private val repo: AuthRepository = AuthRepositoryImpl(mockAuth, mockFirestore)

 @Test
 fun `login should return Success with valid credentials`() = testScope.runTest {
  // 1. Mock de FirebaseUser
  val mockUser = mockk<FirebaseUser> {
   every { uid } returns "123"
   every { email } returns "test@example.com"
  }

  // 2. Mock de AuthResult y Task (¡Clave para await()!)
  val mockAuthResult = mockk<AuthResult> {
   every { user } returns mockUser
  }
  val mockAuthTask = mockk<Task<AuthResult>> {
   // Configuración para corrutinas y listeners
   coEvery { await() } returns mockAuthResult // Retorna resultado al usar await()
   every { isComplete } returns true
   every { isSuccessful } returns true
   every { exception } returns null
   // Dispara listeners manualmente
   every { addOnCompleteListener(any()) } answers {
    firstArg<OnCompleteListener<AuthResult>>().onComplete(this@mockk)
    this@mockk
   }
  }
  every { mockAuth.signInWithEmailAndPassword(any(), any()) } returns mockAuthTask

  // 3. Mock de Firestore (¡Configuración crítica!)
  val mockSnapshot = mockk<DocumentSnapshot> {
   every { exists() } returns true
   every { getString(any<String>()) } answers {
    if (firstArg<String>() == "name") "Test User" else null
   }
  }
  val mockDocTask = mockk<Task<DocumentSnapshot>> {
   // Configuración para corrutinas y listeners
   coEvery { await() } returns mockSnapshot // Retorna resultado al usar await()
   every { isComplete } returns true
   every { isSuccessful } returns true
   every { exception } returns null
   // Dispara listeners manualmente
   every { addOnCompleteListener(any()) } answers {
    firstArg<OnCompleteListener<DocumentSnapshot>>().onComplete(this@mockk)
    this@mockk
   }
  }
  val mockDocument = mockk<DocumentReference> {
   every { get() } returns mockDocTask
  }
  val mockCollection = mockk<CollectionReference> {
   every { document("123") } returns mockDocument // ¡UID coincide con el mockUser!
  }
  every { mockFirestore.collection("users") } returns mockCollection

  // 4. Ejecutar el login
  val result = repo.login("test@example.com", "password")

  // 5. Verificar el resultado
  assertTrue("El resultado debe ser Success", result is Result.Success)
  val user = (result as Result.Success).data
  assertEquals("123", user.id)
  assertEquals("test@example.com", user.email)
  assertEquals("Test User", user.name)
 }

 }