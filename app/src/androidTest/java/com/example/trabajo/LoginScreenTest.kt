package com.example.trabajo.ui.screens

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import com.example.trabajo.viewmodel.UsuarioViewModel
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val dummyViewModel = UsuarioViewModel()

    @Test
    fun testVistaLogin() {

        composeTestRule.setContent {
            // Usamos un navController de prueba
            val navController = rememberNavController()

            LoginScreen(
                navController = navController,
                viewModel = dummyViewModel
            )
        }

        // Verifica que aparece el título de bienvenida
        composeTestRule.onNodeWithText("Bienvenido").assertIsDisplayed()
        composeTestRule.onNodeWithText("Inicia sesión para continuar").assertIsDisplayed()

        // Verifica que los campos de texto existen
        composeTestRule.onNodeWithText("Correo electrónico").assertIsDisplayed()
        composeTestRule.onNodeWithText("Contraseña").assertIsDisplayed()

        // Probar interacción simple en campos (Escribir datos)
        composeTestRule.onNodeWithText("Correo electrónico")
            .performTextInput("javier@gmail.com")

        composeTestRule.onNodeWithText("Contraseña")
            .performTextInput("1234567890")

        // Asegurar que el texto ingresado se mantiene en pantalla
        composeTestRule.onNodeWithText("javier@gmail.com").assertIsDisplayed()

        // Verificar botón de Ingresar
        composeTestRule.onNodeWithText("Ingresar").assertIsDisplayed()

        // Simular un click en el botón
        composeTestRule.onNodeWithText("Ingresar").performClick()
    }
}