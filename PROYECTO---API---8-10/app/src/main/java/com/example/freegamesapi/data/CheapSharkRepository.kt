package com.example.freegamesapi.data

import com.example.freegamesapi.data.remote.CheapSharkRetrofitClient
import com.example.freegamesapi.model.Oferta
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CheapSharkRepository {

    private val api = CheapSharkRetrofitClient.api

    suspend fun obtenerOfertas(): List<Oferta> = withContext(Dispatchers.IO) {
        val dtos = api.obtenerOfertas()
        CheapSharkMapeadores.listDealDtoToListOferta(dtos)
    }
}
