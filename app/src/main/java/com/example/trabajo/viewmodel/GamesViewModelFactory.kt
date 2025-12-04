package com.example.trabajo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.trabajo.data.remote.dao.GamesDao

class GamesViewModelFactory(private val dao: GamesDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GamesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GamesViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}