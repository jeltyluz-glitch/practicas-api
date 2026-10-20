package com.example.freegamesapi.model

class PersonajeModel(
    var id: Int,
    var nombre: String,
    var estado: String,
    var especie: String,
    var imagen: String,
    var isFavorite: Boolean = false
) {
    fun obtenerDetalles(): String {
        return "Personaje: $nombre ($especie - $estado)"
    }
}
