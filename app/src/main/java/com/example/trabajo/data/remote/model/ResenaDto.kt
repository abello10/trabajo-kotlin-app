package com.example.trabajo.data.remote.model

import com.google.gson.annotations.SerializedName

data class ResenaDto(
    val id: Long,
    @SerializedName("contenido") val description: String,
    @SerializedName("clasificacion") val categoria: String
)

fun ResenaDto.toGame(): Games {
    return Games(
        id = this.id.toInt(),
        title = "XD",
        description = this.description,
        categoria = this.categoria
    )
}