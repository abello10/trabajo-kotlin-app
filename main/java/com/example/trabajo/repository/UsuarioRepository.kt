package com.example.trabajo.repository

import com.example.trabajo.data.remote.RetrofitClient
import com.example.trabajo.data.remote.model.UsuarioDto

class UsuarioRepository {

    private val api = RetrofitClient.apiService

    suspend fun createUsuario(name: String, correo: String, contrasena: String): UsuarioDto {
        val usuario = UsuarioDto(
            id = null,
            name = name,
            correo = correo,
            contrasena = contrasena
        )
        return api.createUsuario(usuario)
    }
}
