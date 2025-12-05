package com.example.trabajo.ui.screens

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import com.example.trabajo.viewmodel.GamesViewModel
import org.junit.Rule
import org.junit.Test

class GamesScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // Si no hay internet en el test, la lista saldrá vacía, pero no deberia caerse la pantalla se supone
    private val dummyViewModel = GamesViewModel()

    @Test
    fun testVistaListaJuegos() {

        composeTestRule.setContent {
            val navController = rememberNavController()
            GamesScreen(
                navController = navController,
                viewModel = dummyViewModel
            )
        }

        // Verificar el título principal
        composeTestRule.onNodeWithText("Lista de Juegos").assertIsDisplayed()

        // Verificar que existe el botón del menú hamburguesa (≡)
        composeTestRule.onNodeWithText("≡").assertIsDisplayed()

        // Intentar abrir el menú lateral
        composeTestRule.onNodeWithText("≡").performClick()

        // Verificar que el menú lateral muestra sus opciones
        // (Puede requerir un pequeño tiempo de espera, pero compose suele manejarlo)
        composeTestRule.onNodeWithText("Crear nueva reseña").assertIsDisplayed()
        composeTestRule.onNodeWithText("Perfil").assertIsDisplayed()
    }
}