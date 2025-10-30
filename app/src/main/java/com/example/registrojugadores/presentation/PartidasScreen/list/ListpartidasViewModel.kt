package com.example.registrojugadores.presentation.PartidasScreen.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registrojugadores.domain.model.Partida
import com.example.registrojugadores.domain.repository.PartidaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListPartidasViewModel @Inject constructor(
    private val repository: PartidaRepository,
) : ViewModel() {

    private val _partidasLocales = MutableStateFlow<List<Partida>>(emptyList())
    val partidasLocales: StateFlow<List<Partida>> = _partidasLocales

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        cargarPartidasLocales()
    }

    fun cargarPartidasLocales() {
        viewModelScope.launch {
            repository.getPartidas()
                .catch { e ->
                    _errorMessage.value = "Error cargando partidas locales: ${e.message}"
                }
                .collect { lista ->
                    _partidasLocales.value = lista
                }
        }
    }


    fun deletePartidaLocal(partida: Partida) {
        viewModelScope.launch {
            try {
                repository.deletePartida(partida)
                _partidasLocales.value = _partidasLocales.value.filterNot {
                    it.partidaId == partida.partidaId
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.value = "Error al eliminar la partida: ${e.message}"
            }
        }
    }
}









