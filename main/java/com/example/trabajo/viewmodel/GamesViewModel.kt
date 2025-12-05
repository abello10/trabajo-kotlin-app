package com.example.trabajo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trabajo.data.remote.model.Games
import com.example.trabajo.repository.GamesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GamesViewModel(
    private val repo: GamesRepository = GamesRepository()
) : ViewModel() {

    private val _games = MutableStateFlow<List<Games>>(emptyList())
    val games: StateFlow<List<Games>> = _games

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    init {
        fetchGames()
    }

    fun fetchGames() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val resultadoApi = repo.getGames()

                _games.value = resultadoApi.map { resena ->
                    Games(
                        id = resena.id?.toInt() ?: 0,
                        title = resena.title,
                        description = resena.description,
                        categoria = resena.categoria
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _loading.value = false
            }
        }
    }

    fun addCrit(title: String, description: String, categoria: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                repo.createGame(title, description, categoria)
                fetchGames()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _loading.value = false
            }
        }
    }

    fun deleteGames(game: Games) {
        viewModelScope.launch {
            _loading.value = true
            try {
                repo.deleteGame(game.id.toLong())
                fetchGames()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _loading.value = false
            }
        }
    }
}



