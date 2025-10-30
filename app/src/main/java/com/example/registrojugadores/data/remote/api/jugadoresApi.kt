package com.example.registrojugadores.data.remote

import retrofit2.http.GET

interface JugadoresApi {
    @GET("Jugadores")
    suspend fun getJugadores(): List<JugadorDto>
}
