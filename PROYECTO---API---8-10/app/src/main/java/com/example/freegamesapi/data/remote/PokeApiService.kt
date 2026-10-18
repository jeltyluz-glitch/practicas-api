package com.example.freegamesapi.data.remote

import retrofit2.http.GET

interface PokeApiService {
    @GET("pokemon?limit=20")
    suspend fun obtenerPokemones(): PokeResponseDto
}
