package com.example.trabajo.data.remote

import com.example.trabajo.data.remote.model.ResenaDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GamesApiService {
    @GET("/api/v1/resenas")
    suspend fun getAllResenas(): List<ResenaDto>

    @POST("/api/v1/resenas")
    suspend fun createResena(@Body resena: ResenaDto): ResenaDto

    @DELETE("/api/v1/resenas/{id}")
    suspend fun deleteResena(@Path("id") id: Long)
}