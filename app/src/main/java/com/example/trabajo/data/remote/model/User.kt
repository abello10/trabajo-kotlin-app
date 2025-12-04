package com.example.trabajo.data.remote.model

import androidx.room.Entity
import androidx.room.PrimaryKey

import com.example.trabajo.data.remote.model.UserError

@Entity(tableName = "users")

data class User (

    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    val name: String = "",
    val correo: String = "",
    val clave: String = "",
    val aceptaTerminos: Boolean = false,
    val errores: UserError = UserError()

)