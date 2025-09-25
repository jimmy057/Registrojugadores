package com.example.registrojugadores.presentation.tictactoe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.registrojugadores.domain.model.Jugador
import com.example.registrojugadores.domain.model.Partida
import com.example.registrojugadores.domain.repository.PartidaRepository
import com.example.registrojugadores.domain.usecase.ObserveJugadorUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.serialization.Serializable
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

@Serializable
data class GameUiState(
    val partidaId: Int? = null,
    val jugadores: List<Jugador> = emptyList(),
    val player1Id: Int? = null,
    val player2Id: Int? = null,
    val currentPlayerId: Int? = null,
    val board: List<Int?> = List(9) { null },
    val winnerId: Int? = null,
    val isDraw: Boolean = false,
    val gameStarted: Boolean = false
)

@HiltViewModel
class GameViewModel @Inject constructor(
    private val observeJugadoresUseCase: ObserveJugadorUseCase,
    private val partidaRepository: PartidaRepository
) : ViewModel() {

    private val _state = MutableStateFlow(GameUiState())
    val state: StateFlow<GameUiState> = _state.asStateFlow()

    // ⭐ EVENTO DE UN SOLO USO: Emite el ID del ganador cuando el juego termina.
    private val _logroEvent = MutableSharedFlow<Int>()
    val logroEvent: SharedFlow<Int> = _logroEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            observeJugadoresUseCase().collectLatest { jugadores ->
                _state.update { it.copy(jugadores = jugadores) }
            }

            val ultimaPartida = partidaRepository.obtenerUltimaPartidaEnCurso()
            ultimaPartida?.let { p ->
                _state.update {
                    it.copy(
                        player1Id = p.jugador1Id,
                        player2Id = p.jugador2Id,
                        currentPlayerId = p.currentPlayerId,
                        board = p.board,
                        winnerId = p.ganadorId,
                        isDraw = p.esFinalizada && p.ganadorId == null,
                        gameStarted = !p.esFinalizada,
                        partidaId = p.partidaId
                    )
                }
            }
        }
    }

    fun selectPlayer1(jugador: Jugador) {
        _state.update { it.copy(player1Id = jugador.jugadorId) }
    }

    fun selectPlayer2(jugador: Jugador) {
        _state.update { it.copy(player2Id = jugador.jugadorId) }
    }

    fun startGame() {
        val s = state.value
        val p1 = s.player1Id
        val p2 = s.player2Id
        if (p1 == null || p2 == null || p1 == p2) return

        val nuevaPartida = Partida(
            partidaId = 0, // Room asignará ID
            fecha = java.time.LocalDateTime.now().toString(),
            jugador1Id = p1,
            jugador2Id = p2,
            currentPlayerId = p1,
            board = List(9) { null },
            ganadorId = null,
            esFinalizada = false
        )

        viewModelScope.launch {
            partidaRepository.insertPartida(nuevaPartida)
            val ultima = partidaRepository.obtenerUltimaPartidaEnCurso()
            _state.update {
                it.copy(
                    board = nuevaPartida.board,
                    currentPlayerId = p1,
                    winnerId = null,
                    isDraw = false,
                    gameStarted = true,
                    partidaId = ultima?.partidaId
                )
            }
        }
    }

    fun onCellClick(index: Int) {
        val s = state.value
        if (!s.gameStarted || s.board[index] != null || s.winnerId != null) return

        val current = s.currentPlayerId ?: return
        val newBoard = s.board.toMutableList()
        newBoard[index] = current

        val winner = checkWinner(newBoard)
        val isDraw = winner == null && newBoard.all { it != null }

        val next = if (current == s.player1Id) s.player2Id else s.player1Id

        // ⭐ Emitir Logro (ANTES de actualizar el estado, para que el evento se envíe
        // inmediatamente al detectar el ganador y solo se envíe una vez).
        if (winner != null) {
            viewModelScope.launch {
                _logroEvent.emit(winner)
            }
        }

        viewModelScope.launch {
            val partidaId = s.partidaId ?: return@launch
            val partidaActualizada = Partida(
                partidaId = partidaId,
                fecha = java.time.LocalDateTime.now().toString(),
                jugador1Id = s.player1Id!!,
                jugador2Id = s.player2Id!!,
                currentPlayerId = if (winner != null || isDraw) null else next,
                board = newBoard,
                ganadorId = winner,
                esFinalizada = winner != null || isDraw
            )
            partidaRepository.actualizarPartida(partidaActualizada)
        }

        _state.update {
            it.copy(
                board = newBoard,
                currentPlayerId = if (winner != null || isDraw) null else next,
                winnerId = winner,
                isDraw = isDraw
            )
        }
    }

    private fun checkWinner(board: List<Int?>): Int? {
        val lines = arrayOf(
            intArrayOf(0,1,2), intArrayOf(3,4,5), intArrayOf(6,7,8),
            intArrayOf(0,3,6), intArrayOf(1,4,7), intArrayOf(2,5,8),
            intArrayOf(0,4,8), intArrayOf(2,4,6)
        )
        for (line in lines) {
            val a = board[line[0]] ?: continue
            val b = board[line[1]] ?: continue
            val c = board[line[2]] ?: continue
            if (a == b && b == c) return a
        }
        return null
    }

    // ⭐ MODIFICACIÓN: Ahora crea una NUEVA partida en la DB al reiniciar.
    fun restartGame() {
        val s = state.value
        val p1 = s.player1Id
        val p2 = s.player2Id

        if (p1 == null || p2 == null) {
            _state.update {
                it.copy(
                    board = List(9) { null },
                    winnerId = null,
                    isDraw = false,
                    currentPlayerId = null,
                    gameStarted = false,
                    partidaId = null // Limpiar si no hay jugadores
                )
            }
            return
        }

        val nuevaPartida = Partida(
            partidaId = 0, // Room asignará ID
            fecha = java.time.LocalDateTime.now().toString(),
            jugador1Id = p1,
            jugador2Id = p2,
            currentPlayerId = p1,
            board = List(9) { null },
            ganadorId = null,
            esFinalizada = false
        )

        viewModelScope.launch {
            partidaRepository.insertPartida(nuevaPartida)
            // Necesitamos el ID de la nueva partida para futuras actualizaciones
            val ultima = partidaRepository.obtenerUltimaPartidaEnCurso()

            // Actualizar el estado con el nuevo ID de Partida
            _state.update {
                it.copy(
                    board = List(9) { null },
                    winnerId = null,
                    isDraw = false,
                    currentPlayerId = p1,
                    gameStarted = true,
                    partidaId = ultima?.partidaId // ⭐ Nuevo ID de partida
                )
            }
        }
    }
}
