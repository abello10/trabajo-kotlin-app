package com.example.trabajo.data.remote.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.trabajo.data.remote.model.Games
import kotlinx.coroutines.flow.Flow

@Dao
interface GamesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGame(games: Games)

    @Query("SELECT * FROM games ORDER BY id DESC")
    fun getAllGames(): Flow<List<Games>>

    @Delete
    suspend fun deleteGames(games: Games)
}