package com.example.registrojugadores.presentation.PartidasScreen.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registrojugadores.domain.model.Jugador
import com.example.registrojugadores.domain.model.Partida
import com.example.registrojugadores.domain.usecasepartida.InsertPartidaUseCase
import com.example.registrojugadores.domain.usecasepartida.GetAllPartidasUseCase
import com.example.registrojugadores.domain.usecasepartida.GetPartidaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.text.SimpleDateFormat
import java.util.*

@HiltViewModel
class EditPartidaViewModel @Inject constructor(
    private val insertPartidaUseCase: InsertPartidaUseCase,
    private val getPartidaUseCase: GetPartidaUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(EditPartidaUiState())
    val state: StateFlow<EditPartidaUiState> = _state.asStateFlow()

    /** Cargar partida a editar y jugadores disponibles */
    fun loadPartida(partidaId: Int?) {
        viewModelScope.launch {
            val jugadores = getAllPlayers() // Aquí se reemplaza por tu GetJugadorUseCase
            _state.update { it.copy(jugadores = jugadores) }

            if (partidaId != null) {
                val partida = getPartidaUseCase(partidaId)
                if (partida != null) {
                    _state.update {
                        EditPartidaUiState.fromPartida(partida).copy(
                            jugador1 = jugadores.find { j -> j.jugadorId == partida.jugador1Id },
                            jugador2 = jugadores.find { j -> j.jugadorId == partida.jugador2Id },
                            ganador = partida.ganadorId?.let { id -> jugadores.find { j -> j.jugadorId == id } }
                        )
                    }
                }
            }
        }
    }

    /** Manejar eventos de UI */
    fun onEvent(event: EditPartidaUiEvent) {
        when (event) {
            is EditPartidaUiEvent.SelectJugador1 -> _state.update { it.copy(jugador1 = event.jugador) }
            is EditPartidaUiEvent.SelectJugador2 -> _state.update { it.copy(jugador2 = event.jugador) }
            is EditPartidaUiEvent.SelectGanador -> _state.update { it.copy(ganador = event.jugador) }
            is EditPartidaUiEvent.ToggleFinalizada -> _state.update { it.copy(esFinalizada = event.value) }
            EditPartidaUiEvent.SavePartida -> savePartida()
        }
    }

    /** Guardar partida en la base de datos */
    private fun savePartida() {
        val currentState = _state.value
        val jugador1Id = currentState.jugador1?.jugadorId ?: return
        val jugador2Id = currentState.jugador2?.jugadorId ?: return
        val ganadorId = currentState.ganador?.jugadorId

        val fecha = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

        val partida = Partida(
            partidaId = currentState.partidaId ?: 0,
            fecha = fecha,
            jugador1Id = jugador1Id,
            jugador2Id = jugador2Id,
            ganadorId = ganadorId,
            esFinalizada = currentState.esFinalizada
        )

        viewModelScope.launch {
            insertPartidaUseCase(partida)
            _state.update { it.copy(saved = true) }
        }
    }

    /** Mock de jugadores por ahora (reemplazar con tu GetJugadorUseCase real) */
    private fun getAllPlayers(): List<Jugador> {
        return listOf()
    }
}