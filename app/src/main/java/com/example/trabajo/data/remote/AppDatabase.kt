package com.example.trabajo.data.remote

import com.example.trabajo.data.remote.model.Games
import com.example.trabajo.data.remote.dao.GamesDao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Games::class], version = 2, exportSchema = false) // <- sube a 2
abstract class AppDatabase : RoomDatabase() {
    abstract fun gamesDao(): GamesDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "games_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
