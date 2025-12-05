package com.example.trabajo.data.remote.model

data class UsuarioDto(
    val id: Long? = null,
    val name: String,
    val correo: String,
    val contrasena: String
)
