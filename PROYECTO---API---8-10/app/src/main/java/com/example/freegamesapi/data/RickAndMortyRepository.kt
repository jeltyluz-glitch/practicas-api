package com.example.freegamesapi.data

import com.example.freegamesapi.data.remote.RickAndMortyRetrofitClient
import com.example.freegamesapi.model.PersonajeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RickAndMortyRepository {
    private val api = RickAndMortyRetrofitClient.api

    suspend fun obtenerPersonajes(): List<PersonajeModel> = withContext(Dispatchers.IO) {
        val response = api.obtenerPersonajes()
        response.results?.let { RickAndMortyMapeadores.listCharacterDtoToListPersonaje(it) } ?: emptyList()
    }
}
