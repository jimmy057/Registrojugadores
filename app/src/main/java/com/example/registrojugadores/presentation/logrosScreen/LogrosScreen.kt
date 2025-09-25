package com.example.registrojugadores.presentation.logrosScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.registrojugadores.domain.model.Logro
import com.example.registrojugadores.presentation.logros.LogrosViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun LogrosScreen(
    jugadorId: Int,
    viewModel: LogrosViewModel = hiltViewModel()
) {
    val logros by viewModel.getLogrosJugador(jugadorId).collectAsState(initial = emptyList())

    var nuevaDescripcion by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = nuevaDescripcion,
            onValueChange = { nuevaDescripcion = it },
            label = { Text("Nuevo logro") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (nuevaDescripcion.isNotBlank()) {
                    val fecha = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
                    val logro = Logro(
                        jugadorId = jugadorId,
                        descripcion = nuevaDescripcion,
                        partidaId = null, // ahora válido
                        fecha = fecha,
                        puntos = 0
                    )
                    viewModel.insertarLogro(logro)
                    nuevaDescripcion = ""
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Agregar logro")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (logros.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No hay logros registrados")
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(logros) { logro ->
                    LogroCard(logro = logro, onDelete = { viewModel.eliminarLogro(it) })
                }
            }
        }
    }
}

@Composable
fun LogroCard(
    logro: Logro,
    onDelete: (Logro) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Descripción: ${logro.descripcion}", style = MaterialTheme.typography.bodyMedium)
                Text("Fecha: ${logro.fecha}", style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = { onDelete(logro) }) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar logro")
            }
        }
    }
}
