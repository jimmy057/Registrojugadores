package com.example.registrojugadores.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jugador")
data class JugadorEntity(
    @PrimaryKey(autoGenerate = true)
    val jugadorId: Int =0,
    val nombres: String,
    val partidas: Int

)