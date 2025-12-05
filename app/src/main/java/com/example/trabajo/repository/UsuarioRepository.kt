package com.example.trabajo.repository

import com.example.trabajo.data.remote.RetrofitClient
import com.example.trabajo.data.remote.model.LoginRequest
import com.example.trabajo.data.remote.model.UsuarioDto
import retrofit2.HttpException

class UsuarioRepository {

    private val api = RetrofitClient.apiService

    suspend fun registrarUsuario(
        name: String,
        correo: String,
        contrasena: String
    ): UsuarioDto {
        val body = UsuarioDto(
            id = null,
            name = name,
            correo = correo,
            contrasena = contrasena
        )
        return api.createUsuario(body)
    }

    suspend fun login(
        correo: String,
        contrasena: String
    ): UsuarioDto? {
        return try {
            val request = LoginRequest(correo = correo, contrasena = contrasena)
            api.login(request)
        } catch (e: HttpException) {
            if (e.code() == 401) {
                null
            } else {
                throw e
            }
        }
    }
}

