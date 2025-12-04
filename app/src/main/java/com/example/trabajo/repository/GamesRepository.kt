package com.example.trabajo.repository

import com.example.trabajo.data.remote.RetrofitClient
import com.example.trabajo.data.remote.model.Resena

class GamesRepository {
    private val api = RetrofitClient.apiService

    suspend fun getGames(): List<Resena> = api.getAllResenas()

    suspend fun createGame(title: String, description: String, categoria: String): Resena {
        val nuevaResena = Resena(
            id = null,
            title = title,
            description = description,
            categoria = categoria
        )
        return api.createResena(nuevaResena)
    }

    suspend fun deleteGame(id: Long) = api.deleteResena(id)
}