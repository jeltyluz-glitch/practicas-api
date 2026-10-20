package com.example.freegamesapi.data

import com.example.freegamesapi.data.remote.RetrofitClient
import com.example.freegamesapi.model.PreguntaTrivia
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OpenTriviaRepository {

    private val api = RetrofitClient.openTriviaApi

    suspend fun obtenerTrivia(): List<PreguntaTrivia> = withContext(Dispatchers.IO) {
        val response = api.obtenerTriviaVideojuegos()
        response.results?.let { OpenTriviaMapeadores.listTriviaDtoToListPregunta(it) } ?: emptyList()
    }
}
