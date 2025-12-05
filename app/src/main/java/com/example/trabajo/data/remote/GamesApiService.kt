package com.example.trabajo.data.remote

import com.example.trabajo.data.remote.model.LoginRequest
import com.example.trabajo.data.remote.model.Resena
import com.example.trabajo.data.remote.model.UsuarioApi
import com.example.trabajo.data.remote.model.UsuarioDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GamesApiService {

    @GET("/api/v1/resenas")
    suspend fun getAllResenas(): List<Resena>

    @POST("/api/v1/resenas")
    suspend fun createResena(@Body resena: Resena): Resena

    @DELETE("/api/v1/resenas/{id}")
    suspend fun deleteResena(@Path("id") id: Long)

    @GET("/api/v1/users")
    suspend fun getAllUsuarios(): List<UsuarioDto>

    @POST("/api/v1/users")
    suspend fun createUsuario(@Body usuario: UsuarioDto): UsuarioDto

    @POST("/api/v1/users/login")
    suspend fun login(@Body request: LoginRequest): UsuarioDto
}
