package com.example.registrojugadores.presentation.PartidasScreen.edit

import com.example.registrojugadores.domain.model.Jugador
import com.example.registrojugadores.domain.model.Partida
import java.time.LocalDate

data class EditPartidaUiState(
    val partidaId: Int? = null,
    val fecha: String = LocalDate.now().toString(),
    val jugador1: Jugador? = null,
    val jugador2: Jugador? = null,
    val ganador: Jugador? = null,
    val esFinalizada: Boolean = false,
    val jugadores: List<Jugador> = emptyList(), // 🔹 Lista de jugadores disponible
    val saved: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) {
    companion object {
        fun fromPartida(partida: Partida): EditPartidaUiState {
            return EditPartidaUiState(
                partidaId = partida.partidaId,
                fecha = partida.fecha,
                jugador1 = null,
                jugador2 = null,
                ganador = null,
                esFinalizada = partida.esFinalizada
            )
        }
    }
}