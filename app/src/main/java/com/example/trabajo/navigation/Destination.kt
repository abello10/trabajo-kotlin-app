package com.example.trabajo.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

enum class Destination (
    val route: String,
    val label: String,
    val icon: ImageVector,
    val contentDescription: String
) {
    RESEnA("games", "Reseñas", Icons.Default.Home,"Reseña"),
    CREARESENA("Newgames","Crear Reseña", Icons.Default.Add,"Crear Reseña"),
    PERFIL("perfil","Perfil", Icons.Default.AccountCircle, "Perfil")
}