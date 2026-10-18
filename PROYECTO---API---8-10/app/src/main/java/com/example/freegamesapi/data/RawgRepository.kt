package com.example.freegamesapi.data

import com.example.freegamesapi.data.remote.RawgRetrofitClient
import com.example.freegamesapi.model.JuegoRawg
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RawgRepository {

    private val api = RawgRetrofitClient.api

    suspend fun obtenerJuegosRawg(busqueda: String? = null): List<JuegoRawg> = withContext(Dispatchers.IO) {
        val response = api.obtenerJuegosRawg(busqueda = busqueda)
        response.results?.let { RawgMapeadores.listRawgGameDtoToListJuegoRawg(it) } ?: emptyList()
    }
}
