package com.example.registrojugadores.presentation.tareas.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun ListJugadorScreen(
    navController: NavController,
    viewModel: ListJugadorViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Jugadores", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(state.jugadores) { jugador ->
                JugadorItem(
                    jugador = jugador,
                    onClick = { viewModel.onJugadorSelected(jugador.jugadorId) }
                )
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                    thickness = 1.dp
                )
            }
        }
    }

    state.navegarAEditar?.let { jugadorId ->
        LaunchedEffect(jugadorId) {
            navController.navigate("editJugador/$jugadorId")
            viewModel.onNavigationDone()
        }
    }
}

@Composable
fun JugadorItem(
    jugador: com.example.registrojugadores.domain.model.Jugador,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp)
    ) {
        Text(text = jugador.nombres, style = MaterialTheme.typography.bodyLarge)
        Text(text = "Partidas: ${jugador.partidas}", style = MaterialTheme.typography.bodyMedium)
        if (jugador.logros.isNotEmpty()) {
            Text(
                text = "Logros: ${jugador.logros.joinToString(", ")}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}


