package com.example.trabajo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.trabajo.data.remote.AppDatabase
import com.example.trabajo.navigation.AppNavigation
import com.example.trabajo.ui.screens.GamesScreen
import com.example.trabajo.ui.theme.TrabajoTheme
import com.example.trabajo.viewmodel.GamesViewModel
import com.example.trabajo.viewmodel.GamesViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dao = AppDatabase.getDatabase(application).gamesDao()
        val factory = GamesViewModelFactory(dao)


        setContent {
            val viewModel: GamesViewModel = viewModel(factory = factory)
            TrabajoTheme(dynamicColor = false) {
                Scaffold(
                    containerColor = MaterialTheme.colorScheme.background
                ) { innerPadding ->
                    Box(Modifier.padding(innerPadding)) {
                        AppNavigation(viewModel)
                    }
                }
            }
        }
    }
}