package com.example.freegamesapi.data

import com.example.freegamesapi.data.remote.OpenTriviaRetrofitClient
import com.example.freegamesapi.model.PreguntaTrivia
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OpenTriviaRepository {

    private val api = OpenTriviaRetrofitClient.api

    suspend fun obtenerTrivia(): List<PreguntaTrivia> = withContext(Dispatchers.IO) {
        val response = api.obtenerTriviaVideojuegos()
        response.results?.let { OpenTriviaMapeadores.listTriviaDtoToListPregunta(it) } ?: emptyList()
    }
}
