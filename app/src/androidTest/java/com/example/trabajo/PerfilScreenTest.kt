package com.example.trabajo.ui.screens

import android.os.Build
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import androidx.test.platform.app.InstrumentationRegistry // <--- IMPORTANTE: Importar esto
import com.example.trabajo.viewmodel.UsuarioViewModel
import org.junit.Rule
import org.junit.Test

class PerfilScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val dummyViewModel = UsuarioViewModel()

    @Test
    fun testVistaPerfil() {

        //Otorga permisos manualmente por la consola antes de iniciar la pantalla o si no falla la pantalla.
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val packageName = instrumentation.targetContext.packageName

        // Sirve para darle permisos a la camara mediante comando tuvo que ser asi porque si no fallaba el Test
        instrumentation.uiAutomation.executeShellCommand("pm grant $packageName android.permission.CAMERA")

        // Damos permiso de almacenamiento según la versión de Android
        if (Build.VERSION.SDK_INT >= 33) {
            instrumentation.uiAutomation.executeShellCommand("pm grant $packageName android.permission.READ_MEDIA_IMAGES")
        } else {
            instrumentation.uiAutomation.executeShellCommand("pm grant $packageName android.permission.READ_EXTERNAL_STORAGE")
        }

        composeTestRule.setContent {
            val navController = rememberNavController()

            PerfilScreen(
                navController = navController,
                viewModel = dummyViewModel
            )
        }

        // Verificar Título
        composeTestRule.onNodeWithText("Mi perfil").assertExists()

        // Verificar Botones de Imagen
        composeTestRule.onNodeWithText("Galería").assertExists()
        composeTestRule.onNodeWithText("Cámara").assertExists()

        // Verificar datos
        composeTestRule.onAllNodesWithText("Nombre").onFirst().assertExists()
        composeTestRule.onAllNodesWithText("Correo").onFirst().assertExists()

        // Verificar Botón de Contraseña
        composeTestRule.onAllNodesWithText("Ver").onFirst().assertExists().performClick()

        // Verificar Botones de Acción
        composeTestRule.onNodeWithText("Editar datos").assertExists()
        composeTestRule.onNodeWithText("Cerrar sesión").assertExists()
    }
}