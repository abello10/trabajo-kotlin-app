package com.example.trabajo.ui.screens

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import com.example.trabajo.viewmodel.UsuarioViewModel
import org.junit.Rule
import org.junit.Test

class RegistroUserScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val dummyViewModel = UsuarioViewModel()

    @Test
    fun testVistaRegistro() {

        composeTestRule.setContent {
            val navController = rememberNavController()

            RegistroUserScreen(
                navController = navController,
                viewModel = dummyViewModel
            )
        }

        // Verifica campos de texto principales
        composeTestRule.onNodeWithText("Nombre").assertIsDisplayed()
        composeTestRule.onNodeWithText("Correo electronico").assertIsDisplayed()
        composeTestRule.onNodeWithText("Contraseña").assertIsDisplayed()

        // Verifica el Checkbox de términos
        composeTestRule.onNodeWithText("Acepto los terminos y condiciones").assertIsDisplayed()

        // Interactuar escribiendo datos
        composeTestRule.onNodeWithText("Nombre").performTextInput("Bastian Abello")
        composeTestRule.onNodeWithText("Correo electronico").performTextInput("BatianAbello@gmail.com")
        composeTestRule.onNodeWithText("Contraseña").performTextInput("secreto123")

        // Verificar que el botón Registrar existe
        composeTestRule.onNodeWithText("Registrar").assertIsDisplayed()

        // Verificar el botón para ir al Login
        composeTestRule.onNodeWithText("Inicia sesión aquí").assertIsDisplayed()
    }
}