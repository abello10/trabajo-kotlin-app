package com.example.trabajo.repository

import com.example.trabajo.data.remote.RetrofitClient
import com.example.trabajo.data.remote.model.Resena
import com.example.trabajo.data.remote.model.UsuarioApi

class GamesRepository {
    private val api = RetrofitClient.apiService

    suspend fun getGames(): List<Resena> = api.getAllResenas()


    suspend fun createGame(title: String, description: String, categoria: String): Resena {
        val usuario = UsuarioApi(
            id = 3L,
            name = "Javier",
            correo = "javier@gmail.com",
            contrasena = "1234567890"
        )

        val nuevaResena = Resena(
            id = null,
            title = title,
            description = description,
            categoria = categoria,
            usuario = usuario
        )

        return api.createResena(nuevaResena)
    }

    suspend fun deleteGame(id: Long) = api.deleteResena(id)
}






