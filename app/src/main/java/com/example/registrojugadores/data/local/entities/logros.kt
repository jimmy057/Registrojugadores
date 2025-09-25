package com.example.registrojugadores.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "logros")
data class LogroEntity(
    @PrimaryKey(autoGenerate = true) val logroId: Long = 0L,
    val jugadorId: Int,
    val descripcion: String,
    val fecha: String,
    val puntos: Int = 0,
    val partidaId: Int? = null
)