package com.example.registrojugadores.presentation.logros

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registrojugadores.domain.model.Logro
import com.example.registrojugadores.domain.usecaselogros.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogrosViewModel @Inject constructor(
    private val insertLogroUseCase: InsertLogroUseCase,
    private val deleteLogroUseCase: DeleteLogroUseCase,
    private val getAllLogrosUseCase: GetAllLogrosUseCase,
    private val getLogrosByJugadorUseCase: GetLogrosByJugadorUseCase
) : ViewModel() {

    val allLogros: StateFlow<List<Logro>> = getAllLogrosUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun getLogrosJugador(jugadorId: Int): StateFlow<List<Logro>> {
        return getLogrosByJugadorUseCase(jugadorId)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    }

    fun insertarLogro(logro: Logro) {
        viewModelScope.launch { insertLogroUseCase(logro) }
    }

    fun eliminarLogro(logro: Logro) {
        viewModelScope.launch { deleteLogroUseCase(logro) }
    }
}


