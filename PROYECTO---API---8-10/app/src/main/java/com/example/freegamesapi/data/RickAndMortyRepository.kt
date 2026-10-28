package com.example.freegamesapi.data

import android.content.Context
import android.util.Log
import com.example.freegamesapi.data.remote.RetrofitClient
import com.example.freegamesapi.data.remote.SqlRequest
import com.example.freegamesapi.model.PersonajeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class RickAndMortyRepository(context: Context) {
    private val api = RetrofitClient.rickAndMortyApi
    private val cloudApi = RetrofitClient.cloudApi

    private val _changesFlow = MutableSharedFlow<Unit>(replay = 1, extraBufferCapacity = 64).apply {
        tryEmit(Unit)
    }
    val personajesFlow: Flow<List<PersonajeModel>> = _changesFlow.map {
        obtenerTodosDeNube()
    }

    private suspend fun obtenerTodosDeNube(): List<PersonajeModel> = withContext(Dispatchers.IO) {
        try {
            val response = cloudApi.ejecutarSql(SqlRequest("SELECT * FROM personajes;"))
            val rows = parseRowsToPersonajes(response.data)
            
            if (rows.isEmpty()) {
                sincronizarInicialDesdeApi()
                val retryResponse = cloudApi.ejecutarSql(SqlRequest("SELECT * FROM personajes;"))
                return@withContext parseRowsToPersonajes(retryResponse.data)
            }
            
            return@withContext rows
        } catch (e: Exception) {
            Log.e("RickAndMortyRepository", "Error al obtener de nube", e)
            emptyList()
        }
    }

    private suspend fun sincronizarInicialDesdeApi() {
        try {
            val response = api.obtenerPersonajes()
            val dtos = response.results ?: return
            val personajesModel = RickAndMortyMapeadores.listCharacterDtoToListPersonaje(dtos)
            
            for (p in personajesModel) {
                val safeName = p.nombre.replace("'", "''")
                val sql = "INSERT OR IGNORE INTO personajes (id, nombre, estado, especie, imagen, is_favorite) VALUES (${p.id}, '$safeName', '${p.estado}', '${p.especie}', '${p.imagen}', 0);"
                try {
                    cloudApi.ejecutarSql(SqlRequest(sql))
                } catch (e: Exception) {
                    Log.e("RickAndMortyRepository", "Error insertando personaje ${p.id}", e)
                }
            }
        } catch (e: Exception) {
            Log.e("RickAndMortyRepository", "Error sincronizando desde API", e)
        }
    }

    private fun parseRowsToPersonajes(dataObj: Any?): List<PersonajeModel> {
        val rows = dataObj as? List<*> ?: return emptyList()
        return rows.mapNotNull { row ->
            val map = row as? Map<*, *> ?: return@mapNotNull null
            PersonajeModel(
                id = (map["id"] as? Number)?.toInt() ?: 0,
                nombre = map["nombre"]?.toString() ?: "",
                estado = map["estado"]?.toString() ?: "",
                especie = map["especie"]?.toString() ?: "",
                imagen = map["imagen"]?.toString() ?: "",
                isFavorite = ((map["is_favorite"] as? Number)?.toInt() ?: 0) == 1
            )
        }
    }

    suspend fun refrescarPersonajes() = withContext(Dispatchers.IO) {
        try {
            val response = api.obtenerPersonajes()
            val dtos = response.results ?: return@withContext
            val personajesModel = RickAndMortyMapeadores.listCharacterDtoToListPersonaje(dtos)
            
            for (p in personajesModel) {
                val safeName = p.nombre.replace("'", "''")
                val safeSql = "INSERT OR IGNORE INTO personajes (id, nombre, estado, especie, imagen, is_favorite) VALUES (${p.id}, '$safeName', '${p.estado}', '${p.especie}', '${p.imagen}', 0);"
                try {
                    cloudApi.ejecutarSql(SqlRequest(safeSql))
                } catch (e: Exception) {
                    // Ignorar
                }
            }
            _changesFlow.tryEmit(Unit)
        } catch (e: Exception) {
            Log.e("RickAndMortyRepository", "Error refrescando personajes", e)
        }
    }

    suspend fun toggleFavorito(id: Int, actualEstado: Boolean) = withContext(Dispatchers.IO) {
        val nuevoEstado = !actualEstado
        val valor = if (nuevoEstado) 1 else 0
        try {
            cloudApi.ejecutarSql(SqlRequest("UPDATE personajes SET is_favorite = $valor WHERE id = $id;"))
            _changesFlow.tryEmit(Unit)
        } catch (e: Exception) {
            Log.e("RickAndMortyRepository", "Error actualizando favorito", e)
        }
    }

    suspend fun contarLocal(): Int = withContext(Dispatchers.IO) {
        try {
            val response = cloudApi.ejecutarSql(SqlRequest("SELECT COUNT(*) as total FROM personajes;"))
            val rows = response.data as? List<*>
            val row = rows?.firstOrNull() as? Map<*, *>
            return@withContext (row?.get("total") as? Number)?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }
}
