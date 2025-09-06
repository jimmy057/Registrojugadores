package com.example.registrojugadores.presentation.tareas.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.registrojugadores.domain.model.Jugador

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListJugadorScreen(
    navController: NavController? = null, // 🔹 Ahora es opcional
    viewModel: ListJugadorViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    // 🔹 Navegación a crear jugador
    if (state.navigateToCreate) {
        navController?.navigate("editJugador") // solo navega si hay navController
        viewModel.onNavigationHandled()
    }

    // 🔹 Navegación a editar jugador
    state.navigateToEditId?.let { id ->
        navController?.navigate("editJugador/$id")
        viewModel.onNavigationHandled()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Lista de jugadores") })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onEvent(ListJugadorUiEvent.CreateNew) },
                modifier = Modifier.testTag("fab_create_jugador")
            ) {
                Text("+")
            }
        }
    ) { padding ->
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.testTag("loading"))
            }
        } else if (state.jugadores.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No hay jugadores registrados")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .testTag("jugador_list")
            ) {
                items(state.jugadores) { jugador ->
                    JugadorCard(
                        jugador = jugador,
                        onClick = {
                            viewModel.onEvent(ListJugadorUiEvent.Edit(jugador.jugadorId))
                        },
                        onDelete = {
                            viewModel.onEvent(ListJugadorUiEvent.Delete(jugador.jugadorId))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun JugadorCard(
    jugador: Jugador,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() }
            .testTag("jugador_card_${jugador.jugadorId}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(jugador.nombres, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Partidas: ${jugador.partidas}")
            }

            TextButton(
                onClick = onClick,
                modifier = Modifier.testTag("edit_button_${jugador.jugadorId}")
            ) { Text("Editar") }

            Spacer(modifier = Modifier.width(8.dp))

            TextButton(
                onClick = onDelete,
                modifier = Modifier.testTag("delete_button_${jugador.jugadorId}")
            ) { Text("Eliminar") }
        }
    }
}