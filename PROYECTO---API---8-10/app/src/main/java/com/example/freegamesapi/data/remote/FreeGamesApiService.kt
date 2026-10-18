package com.example.freegamesapi.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Define los endpoints de la API de FreeToGame.
 * Documentación de la API: https://www.freetogame.com/api-doc
 */
interface FreeGamesApiService {

    /** Obtiene la lista de juegos gratuitos. */
    @GET("games")
    suspend fun obtenerJuegos(
        @Query("platform") plataforma: String? = null,
        @Query("category") categoria: String? = null,
        @Query("sort-by") ordenarPor: String? = null,
    ): List<GameDto>

    /** Obtiene el detalle de un juego gratuito por su id. */
    @GET("game")
    suspend fun obtenerJuegoPorId(
        @Query("id") id: Int,
    ): GameDto
}
