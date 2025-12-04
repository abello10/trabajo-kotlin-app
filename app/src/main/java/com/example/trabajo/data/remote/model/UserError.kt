package com.example.trabajo.data.remote.model

data class UserError (
    val nombre: String? = null,
    val correo: String? = null,
    val clave: String? = null,
    val aceptaTerminos: Boolean = false
)