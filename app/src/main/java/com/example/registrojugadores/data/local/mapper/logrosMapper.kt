package com.example.registrojugadores.data.local.mapper

import com.example.registrojugadores.data.local.entities.LogroEntity
import com.example.registrojugadores.domain.model.Logro

object LogroMapper {
    fun toDomain(entity: LogroEntity) = Logro(
        logroId = entity.logroId,
        jugadorId = entity.jugadorId,
        partidaId = entity.partidaId,
        descripcion = entity.descripcion,
        fecha = entity.fecha,
        puntos = entity.puntos,
    )

    fun fromDomain(domain: Logro) = LogroEntity(
        logroId = domain.logroId,
        jugadorId = domain.jugadorId,
        partidaId = domain.partidaId,
        descripcion = domain.descripcion,
        fecha = domain.fecha,
        puntos = domain.puntos
    )
}
