package com.example.freegamesapi.data

import com.example.freegamesapi.data.remote.RetrofitClient
import com.example.freegamesapi.model.PokemonModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PokeRepository {
    private val api = RetrofitClient.pokeApi

    suspend fun obtenerPokemones(): List<PokemonModel> = withContext(Dispatchers.IO) {
        val response = api.obtenerPokemones()
        response.results?.let { PokeMapeadores.listPokeDtoToListPokemon(it) } ?: emptyList()
    }
}
