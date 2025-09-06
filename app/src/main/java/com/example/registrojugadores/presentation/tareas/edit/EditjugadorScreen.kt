package com.example.registrojugadores.presentation.tareas.edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch

@Composable
fun EditJugadorScreen(
    jugadorId: Int? = null, // 🔹 ahora soporta parámetro opcional
    viewModel: EditJugadorViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    // 🔹 Cargar jugador existente si viene un id
    LaunchedEffect(jugadorId) {
        if (jugadorId != null) {
            viewModel.loadJugador(jugadorId)
        }
    }

    // 🔹 Mostrar el cuerpo de la UI
    EditJugadorBody(
        state = state,
        onEvent = viewModel::onEvent
    )
}

private fun EditJugadorViewModel.loadJugador(
    jugadorId: Int
) {
}

@Composable
fun EditJugadorBody(
    state: EditJugadorUiState,
    onEvent: (EditJugadorUiEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        // Nombre del jugador
        OutlinedTextField(
            value = state.nombres,
            onValueChange = { onEvent(EditJugadorUiEvent.NombresChanged(it)) },
            label = { Text("Nombre del Jugador") },
            isError = state.nombresError != null,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("input_nombres")
        )
        if (state.nombresError != null) {
            Text(
                text = state.nombresError,
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(Modifier.height(12.dp))

        // Partidas jugadas
        OutlinedTextField(
            value = state.partidas,
            onValueChange = { onEvent(EditJugadorUiEvent.PartidasChanged(it)) },
            label = { Text("Partidas Jugadas") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = state.partidasError != null,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("input_partidas")
        )
        if (state.partidasError != null) {
            Text(
                text = state.partidasError,
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(Modifier.height(16.dp))

        // Botones de acción
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { onEvent(EditJugadorUiEvent.Save) },
                enabled = !state.isSaving,
                modifier = Modifier
                    .weight(1f)
                    .testTag("btn_guardar")
            ) {
                Text("Guardar")
            }

            Spacer(Modifier.width(8.dp))

            if (!state.isNew) { // 🔹 Solo mostrar "Eliminar" si el jugador ya existe
                OutlinedButton(
                    onClick = { onEvent(EditJugadorUiEvent.Delete) },
                    enabled = !state.isDeleting,
                    modifier = Modifier
                        .weight(1f)
                        .testTag("btn_eliminar")
                ) {
                    Text("Eliminar")
                }
            }
        }
    }
}

@Preview
@Composable
private fun EditJugadorBodyPreview() {
    val state = EditJugadorUiState(
        nombres = "Lionel Messi",
        partidas = "120",
        isNew = false
    )
    MaterialTheme {
        EditJugadorBody(state = state) {}
    }
}