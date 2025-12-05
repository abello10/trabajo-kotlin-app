package com.example.trabajo.ui.screens

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import com.example.trabajo.viewmodel.GamesViewModel
import org.junit.Rule
import org.junit.Test

class CrateGamesScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val dummyViewModel = GamesViewModel()

    @Test
    fun testVistaCrearResena() {

        composeTestRule.setContent {
            val navController = rememberNavController()

            CrateGamesScreen(
                navController = navController,
                viewModel = dummyViewModel
            )
        }
        // Verificar título de la TopAppBar
        composeTestRule.onNodeWithText("Crear Reseña de Juego").assertIsDisplayed()

        // Verificar que existen los inputs
        composeTestRule.onNodeWithText("Titulo").assertIsDisplayed()
        composeTestRule.onNodeWithText("Descripción").assertIsDisplayed()
        composeTestRule.onNodeWithText("Categoría").assertIsDisplayed()

        // Simular escritura de una reseña
        composeTestRule.onNodeWithText("Titulo").performTextInput("Super Mario")
        composeTestRule.onNodeWithText("Descripción").performTextInput("Juego de plataformas clásico")
        composeTestRule.onNodeWithText("Categoría").performTextInput("Aventura")

        // Verificar botón de acción
        composeTestRule.onNodeWithText("Agregar Juego").assertIsDisplayed()

        // Intentar hacer click
        composeTestRule.onNodeWithText("Agregar Juego").performClick()
    }
}