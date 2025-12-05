package com.example.trabajo.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.trabajo.data.session.isLoggedIn
import com.example.trabajo.ui.screens.ActUserScreen
import com.example.trabajo.ui.screens.CrateGamesScreen
import com.example.trabajo.ui.screens.RegistroUserScreen
import com.example.trabajo.viewmodel.UsuarioViewModel
import com.example.trabajo.viewmodel.GamesViewModel
import com.example.trabajo.ui.screens.GamesScreen
import com.example.trabajo.ui.screens.PerfilScreen
import kotlinx.coroutines.flow.first
import androidx.compose.runtime.getValue
import com.example.trabajo.ui.screens.LoginScreen

@Composable
fun AppNavigation(gamesViewModel: GamesViewModel) {
    val navController = rememberNavController()
    val ctx = LocalContext.current
    val usuarioViewModel: UsuarioViewModel = viewModel()


    val loggedIn by produceState<Boolean?>(initialValue = null) {
        value = isLoggedIn(ctx).first()
    }

    if (loggedIn == null) {
        Box(Modifier.fillMaxSize())
        return
    }
    NavHost(
        navController = navController,
        startDestination = if (loggedIn == true) "games" else "login"
    ){

        composable("login") {
            LoginScreen(navController = navController, viewModel = usuarioViewModel)
        }
        composable("registro"){
            RegistroUserScreen(navController, usuarioViewModel)
        }
        composable("games"){
            GamesScreen(navController = navController, viewModel = gamesViewModel)
        }

        composable("perfil") {
            PerfilScreen(navController = navController, viewModel = usuarioViewModel)
        }

        composable("Actperfil"){
            ActUserScreen(navController = navController, viewModel = usuarioViewModel)
        }

        composable("Newgames"){
            CrateGamesScreen(navController = navController, viewModel = gamesViewModel)
        }
    }
}