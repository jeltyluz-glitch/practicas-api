package com.example.freegamesapi.data

import android.content.Context
import com.example.freegamesapi.data.local.PersonajeDbHelper
import com.example.freegamesapi.data.remote.RetrofitClient
import com.example.freegamesapi.model.PersonajeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class RickAndMortyRepository(context: Context) {
    private val api = RetrofitClient.rickAndMortyApi
    private val dbHelper = PersonajeDbHelper.getInstance(context)

    val personajesFlow: Flow<List<PersonajeModel>> = dbHelper.changesFlow.map {
        dbHelper.obtenerTodos()
    }

    suspend fun refrescarPersonajes() = withContext(Dispatchers.IO) {
        try {
            val response = api.obtenerPersonajes()
            val dtos = response.results ?: return@withContext
            val personajesModel = RickAndMortyMapeadores.listCharacterDtoToListPersonaje(dtos)
            dbHelper.insertarPersonajes(personajesModel)
        } catch (e: Exception) {
            // Modo offline: se conservan los datos de la base de datos local
        }
    }

    suspend fun toggleFavorito(id: Int, actualEstado: Boolean) = withContext(Dispatchers.IO) {
        dbHelper.actualizarFavorito(id, !actualEstado)
    }

    suspend fun contarLocal(): Int = withContext(Dispatchers.IO) {
        dbHelper.contar()
    }
}
