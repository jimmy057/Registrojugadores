package com.example.registrojugadores.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.registrojugadores.presentation.PartidasScreen.edit.EditPartidaScreen
import com.example.registrojugadores.presentation.PartidasScreen.list.ListPartidasScreen
import com.example.registrojugadores.presentation.tareas.list.ListJugadorScreen
import com.example.registrojugadores.presentation.tareas.edit.EditJugadorScreen
import com.example.registrojugadores.presentation.tictactoe.TicTacToeScreen

@Composable
fun RegistroJugadoresNavHost(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = "jugador_list"
    ) {
        composable("jugador_list") {
            ListJugadorScreen(
                onNavigateToCreate = { navHostController.navigate("edit_jugador/null") },
                onNavigateToEdit = { id -> navHostController.navigate("edit_jugador/$id") },
                onNavigateToTicTacToe = { navHostController.navigate("tic_tac_toe") },
                onNavigateToPartidas = { navHostController.navigate("partida_list") }
            )
        }
        composable("edit_jugador/{jugadorId}") { backStackEntry ->
            val jugadorId = backStackEntry.arguments?.getString("jugadorId")?.toIntOrNull()
            EditJugadorScreen(
                jugadorId = jugadorId,
                onSaveSuccess = { navHostController.popBackStack() }
            )
        }
        composable("partida_list") {
            ListPartidasScreen(
                onNavigateToDetail = { partidaId ->
                    // Aquí puedes navegar a una pantalla de detalle o edición
                    navHostController.navigate("edit_partida/$partidaId")
                }
            )
        }
        composable("edit_partida/{partidaId}") { backStackEntry ->
            val partidaId = backStackEntry.arguments?.getString("partidaId")?.toIntOrNull()
            EditPartidaScreen(
                partidaId = partidaId,
                onSaveSuccess = { navHostController.popBackStack() }
            )
        }

        composable("tic_tac_toe") {
            TicTacToeScreen()
        }
    }
}


