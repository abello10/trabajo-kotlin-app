package com.example.trabajo.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trabajo.data.remote.model.User
import com.example.trabajo.data.remote.model.UserError
import com.example.trabajo.data.session.setLoggedIn
import com.example.trabajo.data.session.setUser
import com.example.trabajo.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsuarioViewModel : ViewModel() {

    private val repository = UsuarioRepository()

    // -------- ESTADO FORMULARIO --------
    private val _estado = MutableStateFlow(User())
    val estado: StateFlow<User> = _estado

    fun onNombreChange(valor: String) {
        _estado.update {
            it.copy(
                name = valor,
                errores = it.errores.copy(nombre = null)
            )
        }
    }

    fun onCorreoChange(valor: String) {
        _estado.update {
            it.copy(
                correo = valor,
                errores = it.errores.copy(correo = null)
            )
        }
    }

    fun onContrasenaChange(valor: String) {
        _estado.update {
            it.copy(
                contrasena = valor,
                errores = it.errores.copy(contrasena = null)
            )
        }
    }

    fun onAceptarTerminosChange(valor: Boolean) {
        _estado.update { it.copy(aceptaTerminos = valor) }
    }

    fun validarFormulario(): Boolean {
        val estadoActual = _estado.value

        val errores = UserError(
            nombre = if (estadoActual.name.isBlank()) "Campo obligatorio" else null,
            correo = if (
                !estadoActual.correo.contains("@gmail.com") &&
                !estadoActual.correo.contains("@duocuc.cl")
            ) "Correo invalido" else null,
            contrasena = if (estadoActual.contrasena.length < 6)
                "Debe tener al menos 6 caracteres" else null
        )

        val hayErrores = listOfNotNull(
            errores.nombre,
            errores.correo,
            errores.contrasena
        ).isNotEmpty()

        _estado.update { it.copy(errores = errores) }
        return !hayErrores
    }

    fun reset() {
        _estado.value = User()
    }

    // -------- ESTADO LOGIN --------
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _loginError = MutableStateFlow<String?>(null)
    val loginError: StateFlow<String?> = _loginError

    fun clearLoginError() {
        _loginError.value = null
    }

    // -------- REGISTRO: API + DATASTORE --------
    fun registrarUsuario(
        context: Context,
        onResultado: (Boolean) -> Unit
    ) {
        val estadoActual = _estado.value

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val creado = repository.registrarUsuario(
                    name = estadoActual.name,
                    correo = estadoActual.correo,
                    contrasena = estadoActual.contrasena
                )

                // Guardamos sesión en el teléfono
                setLoggedIn(context, true)
                setUser(context, creado.id ?: 0L, creado.name, creado.correo, estadoActual.contrasena)

                onResultado(true)
            } catch (e: Exception) {
                e.printStackTrace()
                onResultado(false)
            } finally {
                _isLoading.value = false
            }
        }
    }

    // -------- LOGIN: API + DATASTORE --------
    fun login(
        context: Context,
        correo: String,
        contrasena: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _loginError.value = null
            try {
                val usuario = repository.login(correo, contrasena)
                if (usuario != null) {
                    setLoggedIn(context, true)
                    setUser(context, usuario.id ?: 0L, usuario.name, usuario.correo, contrasena)
                    onSuccess()
                } else {
                    _loginError.value = "Correo o contraseña incorrectos"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _loginError.value = "Error al conectar con el servidor"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
