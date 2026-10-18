package com.example.freegamesapi.data

import com.example.freegamesapi.data.remote.GameDto
import com.example.freegamesapi.model.Juego
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Mapeadores {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    /**
     * Convierte un GameDto (respuesta de la API) a la clase de dominio Juego.
     */
    fun gameDtoToJuego(dto: GameDto): Juego {
        val fecha = try {
            dto.releaseDate?.let { dateFormat.parse(it) } ?: Date()
        } catch (e: Exception) {
            Date()
        }

        return Juego(
            id = dto.id,
            nombre = dto.title,
            descripcion = dto.shortDescription ?: dto.description ?: "Sin descripción",
            urlDescarga = dto.gameUrl ?: "",
            fechaLanzamiento = fecha,
            desarrollador = dto.developer ?: dto.publisher ?: "Desconocido",
            imagen = dto.thumbnail ?: "",
        )
    }

    /**
     * Convierte una lista de GameDto a una lista de objetos Juego.
     */
    fun listGameDtoToListJuego(dtos: List<GameDto>): List<Juego> {
        return dtos.map { gameDtoToJuego(it) }
    }
}
