package com.example.freegamesapi.data.remote

import retrofit2.http.GET

interface RickAndMortyApiService {
    @GET("character")
    suspend fun obtenerPersonajes(): RickAndMortyResponseDto
}
