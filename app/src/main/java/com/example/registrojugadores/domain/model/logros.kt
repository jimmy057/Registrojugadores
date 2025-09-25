package com.example.registrojugadores.domain.model

data class Logro(
    val logroId: Long = 0L,
    val id: Int = 0,
    val jugadorId: Int,
    val descripcion: String,
    val fecha: String,
    val puntos: Int = 0,
    val partidaId: Int? = null
)