package com.example.trabajo.data.remote.model

data class User(
    val id: Int = 0,
    val name: String = "",
    val correo: String = "",
    val contrasena: String = "",
    val aceptaTerminos: Boolean = false,
    val errores: UserError = UserError()
)
