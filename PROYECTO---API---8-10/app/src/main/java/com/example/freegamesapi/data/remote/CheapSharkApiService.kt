package com.example.freegamesapi.data.remote

import retrofit2.http.GET

interface CheapSharkApiService {

    @GET("deals")
    suspend fun obtenerOfertas(): List<DealDto>
}
