package com.example.trabajo.ui.screens

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import com.example.trabajo.viewmodel.UsuarioViewModel
import org.junit.Rule
import org.junit.Test

class ActUserScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val dummyViewModel = UsuarioViewModel()

    @Test
    fun testVistaActualizarUsuario() {

        composeTestRule.setContent {
            val navController = rememberNavController()
            ActUserScreen(
                navController = navController,
                viewModel = dummyViewModel
            )
        }

        // Verificar Título de la TopBar
        composeTestRule.onNodeWithText("Mi perfil").assertIsDisplayed()

        // Verificar campos de edición
        composeTestRule.onNodeWithText("Nombre").assertIsDisplayed()
        composeTestRule.onNodeWithText("Correo electronico").assertIsDisplayed()
        composeTestRule.onNodeWithText("Contraseña").assertIsDisplayed()

        // Simular escribir un nuevo nombre
        composeTestRule.onNodeWithText("Nombre").performClick()
        composeTestRule.onNodeWithText("Nombre").performTextInput(" Nuevo") // Agrega texto

        // Verificar el botón de acción
        composeTestRule.onNodeWithText("Actualizar").assertIsDisplayed()
        composeTestRule.onNodeWithText("Actualizar").performClick()
    }
}