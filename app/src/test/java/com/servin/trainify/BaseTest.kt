package com.servin.trainify

import io.mockk.MockKAnnotations
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before

open class BaseTest { // 1. 'open' permite heredar esta clase
    // 2. Configuración de Corrutinas para Testing
    @OptIn(ExperimentalCoroutinesApi::class)
    protected val testDispatcher = UnconfinedTestDispatcher()
    protected val testScope = TestScope(testDispatcher)

    // 3. Método de inicialización que se ejecuta antes de cada test
    @Before
    open fun setup() {
        MockKAnnotations.init(this) // 4. Inicializa los mocks de MockK
    }
}