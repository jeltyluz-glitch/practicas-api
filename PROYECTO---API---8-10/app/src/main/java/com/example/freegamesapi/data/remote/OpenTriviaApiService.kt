package com.example.freegamesapi.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface OpenTriviaApiService {

    @GET("api.php")
    suspend fun obtenerTriviaVideojuegos(
        @Query("amount") cantidad: Int = 10,
        @Query("category") categoria: Int = 15,
        @Query("type") tipo: String = "multiple",
    ): TriviaResponseDto
}
