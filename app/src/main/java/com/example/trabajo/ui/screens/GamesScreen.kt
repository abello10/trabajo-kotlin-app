package com.example.trabajo.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController

import com.example.trabajo.data.remote.model.Games
import com.example.trabajo.navigation.AppNavigation
import com.example.trabajo.navigation.NavigationRailE
import com.example.trabajo.viewmodel.GamesViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GamesScreen(navController: NavController, viewModel: GamesViewModel, modifier: Modifier = Modifier) {
    val juegos by viewModel.games.collectAsState()

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color(0xFF1B0D3F),   // ← fondo del panel (morado/pon el que quieras)
                drawerContentColor   = Color.White,

            ) {

                NavigationDrawerItem(
                    label = { Text("Juegos")},
                    selected = true,
                    onClick = { scope.launch { drawerState.close() } },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary, // Esto sirve para la cosa que dejemos puesta resalte
                        selectedTextColor      = Color.White
                    )
                )
                NavigationDrawerItem(
                    label = { Text("Perfil", color = MaterialTheme.colorScheme.onPrimary) },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate("perfil")
                    }
                )
                NavigationDrawerItem(
                    label = {Text("Crear nueva reseña", color = MaterialTheme.colorScheme.onPrimary)},
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate("Newgames")
                    }
                )
            }
        }
    ) {

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Lista de Juegos", color = MaterialTheme.colorScheme.onPrimary) },
                navigationIcon = {
                    IconButton(onClick = { scope.launch { drawerState.open() } }) {
                        Text("≡",
                            fontSize = 40.sp) // Con eso podemos tener el estilo hamburguesa para la APP
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(juegos) { games ->
                    GamesItem(games) { viewModel.deleteGames(it)}
                }
            }
        }

        }
    }
}

@Composable
fun GamesItem(g: Games, onDelete: (Games) -> Unit) {
    var ask by remember { mutableStateOf(false) }
    if (ask) AlertDialog(
        onDismissRequest = { ask = false },
        title = { Text("¿Estas seguro que quieres eliminar \"${g.title}\"?") },
        confirmButton = { TextButton({ ask = false; onDelete(g) }, colors = ButtonDefaults.textButtonColors(contentColor = Color.White)) { Text("Eliminar") } },
        dismissButton = { TextButton({ ask = false }, colors = ButtonDefaults.textButtonColors(contentColor = Color.White)) { Text("Cancelar") }
        },
        containerColor = Color(0xFF2B1663),
        titleContentColor = Color.White,
        textContentColor = Color.White
    )

    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(Modifier.padding(12.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(g.title, style = MaterialTheme.typography.titleMedium)
                Text(g.description, style = MaterialTheme.typography.titleSmall, color = Color.DarkGray)
                Text(g.categoria, style = MaterialTheme.typography.labelMedium, color = Color.Red)

            }
            Text("✕", Modifier.clickable { ask = true }.padding(start = 8.dp))
        }
    }
}

