package com.example.registrojugadores

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.ExperimentalMaterial3Api
import dagger.hilt.android.AndroidEntryPoint
import com.example.registrojugadores.presentation.tareas.edit.EditJugadorScreen
import com.example.registrojugadores.presentation.tareas.list.ListJugadorScreen
import com.example.registrojugadores.ui.theme.RegistroJugadoresTheme



@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegistroJugadoresTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "listJugador"
                ) {
                    composable("listJugador") {
                        ListJugadorScreen(
                            onNavigateToCreate = { navController.navigate("editJugador") },
                            onNavigateToEdit = { id -> navController.navigate("editJugador/$id") }
                        )
                    }
                    composable("editJugador") {
                        EditJugadorScreen(
                            onSaveSuccess = { navController.popBackStack() }
                        )
                    }
                    composable("editJugador/{id}") { backStackEntry ->
                        val id = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: 0
                        EditJugadorScreen(
                            jugadorId = id,
                            onSaveSuccess = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
