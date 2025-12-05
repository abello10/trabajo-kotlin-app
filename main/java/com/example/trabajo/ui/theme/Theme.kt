package com.example.trabajo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Purple700,                 // AppBar, etc.
    onPrimary = Color.White,

    background = Purple900,              // Fondo general
    onBackground = Color.White,

    surface = Purple900,                 // Por compatibilidad con Scaffold
    onSurface = Color.White,

    surfaceVariant = Purple800,          // Cards / paneles
    onSurfaceVariant = Color.White,

    tertiary = AccentYellow,             // Botones de acción (tu amarillo)
    onTertiary = Color.Black
)

private val DarkColors = LightColors     // para simplificar (misma paleta)

@Composable
fun TrabajoTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,       // mantenlo false para que NO pise tu paleta
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (useDarkTheme) DarkColors else LightColors,
        typography = Typography(),       // deja el default; si tienes Type.kt puedes usarlo
        content = content
    )
}
