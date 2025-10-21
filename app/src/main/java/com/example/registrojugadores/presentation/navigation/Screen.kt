package com.example.registrojugadores.presentation.navigation

sealed class Screen(val route: String) {

    object JugadorList : Screen("jugador_list")

    object TicTacToe : Screen("tic_tac_toe")

    data class EditJugador(val jugadorId: Int?) : Screen(
        jugadorId?.let { "edit_jugador/$it" } ?: "edit_jugador/null"
    )

    object PartidaList : Screen("partida_list")

    data class EditPartida(val partidaId: Int?) : Screen(
        partidaId?.let { "edit_partida/$it" } ?: "edit_partida/null"
    )
}



