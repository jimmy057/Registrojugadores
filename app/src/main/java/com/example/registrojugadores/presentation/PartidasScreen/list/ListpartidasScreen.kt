package com.example.registrojugadores.presentation.PartidasScreen.list

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.registrojugadores.domain.model.Partida

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListPartidasScreen(
    viewModel: ListPartidasViewModel = hiltViewModel(),
    onNavigateToGame: (Int) -> Unit
) {
    val partidasLocales by viewModel.partidasLocales.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Locales", "API")

    val partidas = if (selectedTab == 0) partidasLocales else TODO()
    val mensajeVacio = if (selectedTab == 0) "No hay partidas locales" else TODO()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Partidas registradas") })
        },
        floatingActionButton = {
            if (selectedTab == 1) {
                FloatingActionButton(onClick = { viewModel.cargarPartidasLocales() }) {
                    Text("🔄")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                errorMessage != null -> {
                    Text(
                        text = errorMessage ?: "",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                partidas.isEmpty() -> {
                    Text(
                        text = mensajeVacio,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                else -> {
                    PartidasList(
                        partidas = partidas,
                        onClick = onNavigateToGame,
                        onDelete = if (selectedTab == 0) viewModel::deletePartidaLocal else null
                    )
                }
            }
        }
    }
}

@Composable
fun PartidasList(
    partidas: List<Partida>,
    onClick: (Int) -> Unit,
    onDelete: ((Partida) -> Unit)?
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(partidas) { partida ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable(
                        onClick = { onClick(partida.partidaId) },
                        interactionSource = remember { MutableInteractionSource() },
                        indication = LocalIndication.current
                    ),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Partida ID: ${partida.partidaId}")
                    Text("Fecha: ${partida.fecha}")
                    Text("Jugador 1 ID: ${partida.jugador1Id}")
                    Text("Jugador 2 ID: ${partida.jugador2Id}")
                    Text("Ganador ID: ${partida.ganadorId ?: "Pendiente"}")
                    Text("Finalizada: ${if (partida.esFinalizada) "Sí" else "No"}")

                    if (onDelete != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = { onDelete(partida) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error,
                                contentColor = MaterialTheme.colorScheme.onError
                            )
                        ) {
                            Text("Eliminar partida")
                        }
                    }
                }
            }
        }
    }
}







