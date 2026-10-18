package com.example.freegamesapi.data

import com.example.freegamesapi.data.remote.RawgGameDto
import com.example.freegamesapi.model.JuegoRawg

object RawgMapeadores {

    fun rawgGameDtoToJuegoRawg(dto: RawgGameDto): JuegoRawg {
        return JuegoRawg(
            id = dto.id,
            nombre = dto.name ?: "Sin nombre",
            imagen = dto.backgroundImage ?: "",
            rating = dto.rating ?: 0.0,
            fechaLanzamiento = dto.released ?: "Desconocida",
        )
    }

    fun listRawgGameDtoToListJuegoRawg(dtos: List<RawgGameDto>): List<JuegoRawg> {
        return dtos.map { rawgGameDtoToJuegoRawg(it) }
    }
}
