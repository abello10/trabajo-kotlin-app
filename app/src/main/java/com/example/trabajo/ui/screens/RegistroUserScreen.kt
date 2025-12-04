package com.example.trabajo.ui.screens
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.trabajo.data.session.setLoggedIn
import com.example.trabajo.data.session.setUser
import com.example.trabajo.viewmodel.UsuarioViewModel
import kotlinx.coroutines.launch

@Composable
fun RegistroUserScreen(
    navController: NavController,
    viewModel: UsuarioViewModel
){
    val estado by viewModel.estado.collectAsState()

    val pasosCompletados = listOf(
        estado.name.isNotBlank(),
        android.util.Patterns.EMAIL_ADDRESS.matcher(estado.correo).matches(),
        estado.clave.length >= 6,
        estado.aceptaTerminos
    ).count { it }

    val targetProgress = pasosCompletados / 4f
    val animatedProgress by animateFloatAsState(targetValue = targetProgress, label = "registroProgress")

    val ctx = LocalContext.current
    val scope = rememberCoroutineScope()

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        //Nombre
        OutlinedTextField(
            value = estado.name,
            onValueChange = viewModel::onNombreChange,
            label = { Text("Nombre", color = MaterialTheme.colorScheme.onPrimary) },
            isError = estado.errores.nombre != null,
            supportingText = {
                estado.errores.nombre?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        //Correo
        OutlinedTextField(
            value = estado.correo,
            onValueChange = viewModel::onCorreoChange,
            label = {Text("Correo electronico", color = MaterialTheme.colorScheme.onPrimary)},
            isError = estado.errores.correo != null,
            supportingText = {
                estado.errores.correo?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Clave
        OutlinedTextField(
            value = estado.clave,
            onValueChange = viewModel::onClaveChange,
            label = { Text("Contraseña", color = MaterialTheme.colorScheme.onPrimary)},
            visualTransformation = PasswordVisualTransformation(),
            isError = estado.errores.clave != null,
            supportingText = {
                estado.errores.clave?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = estado.aceptaTerminos,
                onCheckedChange = viewModel::onAceptarTerminosChange
            )
            Spacer(Modifier.width(8.dp))
            Text("Acepto los terminos y condiciones")
        }

        Spacer(Modifier.height(8.dp))

        LinearProgressIndicator(
            progress = { animatedProgress },
            modifier = Modifier.fillMaxWidth(),
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "${(animatedProgress * 100).toInt()}%",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(Modifier.height(8.dp))


        Button(
            onClick = {
                if (viewModel.validarFormulario()) {
                    scope.launch {
                        setLoggedIn(ctx, true)
                        setUser(ctx, estado.name, estado.correo, estado.clave)
                    }
                    navController.navigate("games") { popUpTo(0)}
                }
            },
            enabled = estado.aceptaTerminos,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar")
        }


    }

}