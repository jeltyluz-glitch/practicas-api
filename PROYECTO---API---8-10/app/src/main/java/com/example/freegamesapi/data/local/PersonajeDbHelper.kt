package com.example.freegamesapi.data.local

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.freegamesapi.model.PersonajeModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class PersonajeDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "personajes.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_PERSONAJES = "personajes"

        private const val COL_ID = "id"
        private const val COL_NOMBRE = "nombre"
        private const val COL_ESTADO = "estado"
        private const val COL_ESPECIE = "especie"
        private const val COL_IMAGEN = "imagen"
        private const val COL_IS_FAVORITE = "is_favorite"

        @Volatile
        private var INSTANCE: PersonajeDbHelper? = null

        fun getInstance(context: Context): PersonajeDbHelper {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: PersonajeDbHelper(context.applicationContext).also { INSTANCE = it }
            }
        }
    }

    private val _changesFlow = MutableSharedFlow<Unit>(replay = 1, extraBufferCapacity = 64, onBufferOverflow = BufferOverflow.DROP_OLDEST).apply {
        tryEmit(Unit)
    }
    val changesFlow: Flow<Unit> = _changesFlow.asSharedFlow()

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_PERSONAJES (
                $COL_ID INTEGER PRIMARY KEY,
                $COL_NOMBRE TEXT,
                $COL_ESTADO TEXT,
                $COL_ESPECIE TEXT,
                $COL_IMAGEN TEXT,
                $COL_IS_FAVORITE INTEGER DEFAULT 0
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PERSONAJES")
        onCreate(db)
    }

    fun obtenerFavoritosIds(): List<Int> {
        val db = readableDatabase
        val ids = mutableListOf<Int>()
        val cursor = db.rawQuery("SELECT $COL_ID FROM $TABLE_PERSONAJES WHERE $COL_IS_FAVORITE = 1", null)
        cursor.use {
            while (it.moveToNext()) {
                ids.add(it.getInt(0))
            }
        }
        return ids
    }

    fun contar(): Int {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM $TABLE_PERSONAJES", null)
        cursor.use {
            if (it.moveToFirst()) return it.getInt(0)
        }
        return 0
    }

    fun insertarPersonajes(nuevos: List<PersonajeModel>) {
        val db = writableDatabase
        db.beginTransaction()
        try {
            val favIds = obtenerFavoritosIds().toSet()
            for (p in nuevos) {
                val isFav = if (favIds.contains(p.id) || p.isFavorite) 1 else 0
                val values = ContentValues().apply {
                    put(COL_ID, p.id)
                    put(COL_NOMBRE, p.nombre)
                    put(COL_ESTADO, p.estado)
                    put(COL_ESPECIE, p.especie)
                    put(COL_IMAGEN, p.imagen)
                    put(COL_IS_FAVORITE, isFav)
                }
                db.insertWithOnConflict(TABLE_PERSONAJES, null, values, SQLiteDatabase.CONFLICT_REPLACE)
            }
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
        _changesFlow.tryEmit(Unit)
    }

    fun actualizarFavorito(id: Int, isFavorite: Boolean) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_IS_FAVORITE, if (isFavorite) 1 else 0)
        }
        db.update(TABLE_PERSONAJES, values, "$COL_ID = ?", arrayOf(id.toString()))
        _changesFlow.tryEmit(Unit)
    }

    fun obtenerTodos(): List<PersonajeModel> {
        val db = readableDatabase
        val lista = mutableListOf<PersonajeModel>()
        val cursor = db.rawQuery("SELECT * FROM $TABLE_PERSONAJES", null)
        cursor.use {
            val idxId = it.getColumnIndex(COL_ID)
            val idxNombre = it.getColumnIndex(COL_NOMBRE)
            val idxEstado = it.getColumnIndex(COL_ESTADO)
            val idxEspecie = it.getColumnIndex(COL_ESPECIE)
            val idxImagen = it.getColumnIndex(COL_IMAGEN)
            val idxFav = it.getColumnIndex(COL_IS_FAVORITE)

            while (it.moveToNext()) {
                lista.add(
                    PersonajeModel(
                        id = it.getInt(idxId),
                        nombre = it.getString(idxNombre) ?: "",
                        estado = it.getString(idxEstado) ?: "",
                        especie = it.getString(idxEspecie) ?: "",
                        imagen = it.getString(idxImagen) ?: "",
                        isFavorite = it.getInt(idxFav) == 1
                    )
                )
            }
        }
        return lista
    }
}
