package com.example.registrojugadores.presentation.PartidasScreen.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registrojugadores.domain.model.Partida
import com.example.registrojugadores.domain.repository.PartidaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListPartidasViewModel @Inject constructor(
    private val repository: PartidaRepository
) : ViewModel() {

    val partidas = repository.getPartidas()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun deletePartidas(partida: Partida) {
        viewModelScope.launch {
            repository.deletePartida(partida)
        }
    }
}