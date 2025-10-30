package com.example.registrojugadores.presentation.tareas.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registrojugadores.domain.usecase.ObserveJugadorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ListJugadorViewModel @Inject constructor(
    private val observeJugadorUseCase: ObserveJugadorUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ListJugadorUiState())
    val state: StateFlow<ListJugadorUiState> = _state.asStateFlow()

    init {
        loadJugadores()
    }

    private fun loadJugadores() {
        observeJugadorUseCase()
            .onEach { jugadores ->
                _state.value = _state.value.copy(jugadores = jugadores)
            }
            .launchIn(viewModelScope)
    }

    fun onJugadorSelected(jugadorId: Int) {
        _state.value = _state.value.copy(navegarAEditar = jugadorId)
    }

    fun onNavigationDone() {
        _state.value = _state.value.copy(navegarAEditar = null)
    }
}

