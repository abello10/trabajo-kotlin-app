package com.example.trabajo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trabajo.navigation.AppNavigation
import com.example.trabajo.ui.theme.TrabajoTheme
import com.example.trabajo.viewmodel.GamesViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: GamesViewModel = viewModel()

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

