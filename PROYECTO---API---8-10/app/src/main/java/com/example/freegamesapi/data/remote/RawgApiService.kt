package com.example.freegamesapi.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface RawgApiService {

    @GET("games")
    suspend fun obtenerJuegosRawg(
        @Query("key") apiKey: String = "d48633c706ee4e30b6ffc3a5a8a6142c",
        @Query("search") busqueda: String? = null,
    ): RawgResponseDto
}
