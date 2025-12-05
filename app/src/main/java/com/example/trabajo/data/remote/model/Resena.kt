package com.example.trabajo.data.remote.model


data class UsuarioApi(
    val id: Long?,
    val name: String? = null,
    val correo: String? = null,
    val contrasena: String? = null
)

data class Resena(
    val id: Long? = null,
    val title: String,
    val description: String,
    val categoria: String,
    val usuario: UsuarioApi
)

