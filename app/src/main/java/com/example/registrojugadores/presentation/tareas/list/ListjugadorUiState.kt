package com.example.registrojugadores.presentation.tareas.list

import com.example.registrojugadores.domain.model.Jugador

data class ListJugadorUiState(
    val isLoading: Boolean = false,
    val jugadores: List<Jugador> = emptyList(),
    val message: String? = null,
    val navigateToCreate: Boolean = false,
    val navigateToEditId: Int? = null
)