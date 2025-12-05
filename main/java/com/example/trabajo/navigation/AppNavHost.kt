package com.example.trabajo.navigation

import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import com.example.trabajo.ui.screens.CrateGamesScreen
import com.example.trabajo.ui.screens.GamesScreen
import com.example.trabajo.ui.screens.PerfilScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.trabajo.viewmodel.GamesViewModel
import com.example.trabajo.viewmodel.UsuarioViewModel

@Composable
fun AppNavHost(
   navController: NavHostController,
   startDestination: Destination,
   modifier: Modifier = Modifier
){
    val usuarioViewModel: UsuarioViewModel = viewModel()
    val gamesViewModel: GamesViewModel = viewModel()

    NavHost(
        navController,
        startDestination = startDestination.route
    ){
        Destination.entries.forEach { destination ->
            composable(destination.route) {
                when(destination){
                    Destination.RESEnA -> GamesScreen(navController = navController, viewModel = gamesViewModel)
                    Destination.CREARESENA -> CrateGamesScreen(navController = navController,viewModel = gamesViewModel)
                    Destination.PERFIL -> PerfilScreen(navController = navController,viewModel = usuarioViewModel)
                }
            }
        }
    }
}