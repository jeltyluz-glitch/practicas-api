package com.example.freegamesapi.model

class JuegoRawg(
    var id: Int,
    var nombre: String,
    var imagen: String,
    var rating: Double,
    var fechaLanzamiento: String
) {
    fun obtenerDetalles(): String {
        return "RAWG ID: $id, Nombre: $nombre, Rating: $rating"
    }
}
