package com.example.registrojugadores.presentation.PartidasScreen.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.registrojugadores.domain.model.Partida

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListPartidasScreen(
    viewModel: ListPartidasViewModel = hiltViewModel(),
    onNavigateToDetail: (Int) -> Unit
) {
    val partidas by viewModel.partidas.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Partidas registradas") }) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(partidas) { partida ->
                PartidasCard(
                    partida = partida,
                    onClick = { onNavigateToDetail(partida.partidaId) },
                    onDelete = { viewModel.deletePartidas(partida) }
                )
            }
        }
    }
}

@Composable
fun PartidasCard(
    partida: Partida,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Partida ID: ${partida.partidaId}")
            Text("Fecha: ${partida.fecha}")
            Text("Jugador 1 ID: ${partida.jugador1Id}")
            Text("Jugador 2 ID: ${partida.jugador2Id}")
            Text("Ganador ID: ${partida.ganadorId ?: "Pendiente"}")
            Text("Finalizada: ${if (partida.esFinalizada) "Sí" else "No"}")

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = onDelete, colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)) {
                Text("Eliminar")
            }
        }
    }
}