package com.example.trabajo.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.trabajo.viewmodel.GamesViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrateGamesScreen(navController: NavController, viewModel: GamesViewModel) {


    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }


    var titleError by remember { mutableStateOf<String?>(null) }
    var descError by remember { mutableStateOf<String?>(null) }
    var catError by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()


    fun validarYGuardar() {
        var hayError = false

        if (title.isBlank()) {
            titleError = "Campo obligatorio"
            hayError = true
        }
        if (description.isBlank()) {
            descError = "Campo obligatorio"
            hayError = true
        }
        if (categoria.isBlank()) {
            catError = "Campo obligatorio"
            hayError = true
        }

        if (!hayError) {
            viewModel.addCrit(context, title, description, categoria)
            title = ""
            description = ""
            categoria = ""
            titleError = null
            descError = null
            catError = null

            Toast.makeText(context, "Se creo con exito la reseña", Toast.LENGTH_SHORT).show()
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color(0xFF1B0D3F),
                drawerContentColor = Color.White,
            ) {
                NavigationDrawerItem(
                    label = { Text("Crear reseña") },
                    selected = true,
                    onClick = { scope.launch { drawerState.close() } },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = Color.White
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
                    label = { Text("Lista de Juegos", color = MaterialTheme.colorScheme.onPrimary) },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate("games")
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Crear Reseña de Juego", color = MaterialTheme.colorScheme.onPrimary) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Text("≡", fontSize = 40.sp)
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
                    .fillMaxSize(),

                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        title = it
                        titleError = null
                    },
                    label = { Text("Titulo", color = MaterialTheme.colorScheme.onPrimary) },
                    modifier = Modifier.fillMaxWidth(),
                    isError = titleError != null,
                    // 2. TRUCO: Si no hay error, pasamos 'null' para que no ocupe espacio
                    supportingText = if (titleError != null) {
                        { Text(text = titleError!!, color = MaterialTheme.colorScheme.error) }
                    } else null
                )




                OutlinedTextField(
                    value = description,
                    onValueChange = {
                        description = it
                        descError = null
                    },
                    label = { Text("Descripción", color = MaterialTheme.colorScheme.onPrimary) },
                    modifier = Modifier.fillMaxWidth(),
                    isError = descError != null,
                    supportingText = if (descError != null) {
                        { Text(text = descError!!, color = MaterialTheme.colorScheme.error) }
                    } else null
                )


                OutlinedTextField(
                    value = categoria,
                    onValueChange = {
                        categoria = it
                        catError = null
                    },
                    label = { Text("Categoría", color = MaterialTheme.colorScheme.onPrimary) },
                    modifier = Modifier.fillMaxWidth(),
                    isError = catError != null,
                    supportingText = if (catError != null) {
                        { Text(text = catError!!, color = MaterialTheme.colorScheme.error) }
                    } else null
                )


                Button(
                    onClick = { validarYGuardar() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Agregar Juego")
                }
            }
                Spacer(modifier = Modifier.height(16.dp))
        }
    }

}

