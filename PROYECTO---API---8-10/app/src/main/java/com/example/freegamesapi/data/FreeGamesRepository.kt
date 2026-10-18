package com.example.freegamesapi.data

import com.example.freegamesapi.data.remote.RetrofitClient
import com.example.freegamesapi.model.Juego
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FreeGamesRepository {

    private val api = RetrofitClient.api

    suspend fun obtenerJuegos(
        plataforma: String? = null,
        categoria: String? = null,
        ordenarPor: String? = null,
    ): List<Juego> = withContext(Dispatchers.IO) {
        try {
            val dtos = api.obtenerJuegos(plataforma, categoria, ordenarPor)
            Mapeadores.listGameDtoToListJuego(dtos)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun obtenerJuegoPorId(id: Int): Juego? = withContext(Dispatchers.IO) {
        try {
            val dto = api.obtenerJuegoPorId(id)
            Mapeadores.gameDtoToJuego(dto)
        } catch (e: Exception) {
            null
        }
    }
}
