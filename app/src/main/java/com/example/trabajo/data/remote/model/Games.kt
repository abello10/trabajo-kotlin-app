package com.example.trabajo.data.remote.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")

data class Games (

    @PrimaryKey(autoGenerate = true) val id : Int = 0,

    val title: String,
    val description: String,
    val categoria: String
)