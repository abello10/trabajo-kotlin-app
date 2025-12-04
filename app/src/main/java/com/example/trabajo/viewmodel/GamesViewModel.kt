package com.example.trabajo.viewmodel

import com.example.trabajo.data.remote.model.Games
import com.example.trabajo.data.remote.dao.GamesDao

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GamesViewModel(private val dao: GamesDao) : ViewModel() {

    val games = dao.getAllGames()
        .stateIn(viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList())

    fun addCrit(title: String, description: String, categoria: String){
        viewModelScope.launch {
            dao.addGame(Games(title = title, description = description, categoria = categoria))
        }
    }

    fun deleteGames(games: Games) {
        viewModelScope.launch {
            dao.deleteGames(games)
        }
    }

}