package com.example.trabajo.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

import com.example.trabajo.data.remote.model.User
import com.example.trabajo.data.remote.model.UserError

class UsuarioViewModel : ViewModel() {
    private val _estado = MutableStateFlow(User())

    val estado: StateFlow<User> = _estado

    fun onNombreChange(valor: String) {
        _estado.update { it.copy(name = valor, errores = it.errores.copy(nombre = null)) }
    }

    fun onCorreoChange(valor: String){
        _estado.update { it.copy(correo = valor, errores = it.errores.copy(correo = null)) }
    }

    fun onClaveChange(valor: String){
        _estado.update { it.copy(clave = valor, errores = it.errores.copy(clave= null)) }
    }

    fun onAceptarTerminosChange(valor: Boolean){
        _estado.update { it.copy(aceptaTerminos = valor) }
    }

    fun validarFormulario() : Boolean {
        val estadoActual = _estado.value

        val errores = UserError(
            nombre = if(estadoActual.name.isBlank()) "Campo obligatorio" else null,
            correo = if(!estadoActual.correo.contains("@gmail.com") && !estadoActual.correo.contains("@duocuc.cl")) "Correo invalido" else null,
            clave = if(estadoActual.clave.length < 6) "Debe tener al menos 6 caracteres" else null,

        )

        val hayErrores = listOfNotNull(
            errores.nombre,
            errores.correo,
            errores.clave
        ).isNotEmpty()

        _estado.update { it.copy(errores = errores) }
        return !hayErrores
    }

    fun reset() {
        _estado.value = com.example.trabajo.data.remote.model.User()
    }

}