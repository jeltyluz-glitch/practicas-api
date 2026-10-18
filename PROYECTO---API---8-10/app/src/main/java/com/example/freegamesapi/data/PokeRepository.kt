package com.example.freegamesapi.data

import com.example.freegamesapi.data.remote.PokeRetrofitClient
import com.example.freegamesapi.model.PokemonModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PokeRepository {
    private val api = PokeRetrofitClient.api

    suspend fun obtenerPokemones(): List<PokemonModel> = withContext(Dispatchers.IO) {
        val response = api.obtenerPokemones()
        response.results?.let { PokeMapeadores.listPokeDtoToListPokemon(it) } ?: emptyList()
    }
}
